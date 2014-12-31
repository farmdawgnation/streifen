package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

/**
 * Implementation of the Token structure in Stripe's API.
 * See https://stripe.com/docs/api#token_object
**/
case class Token(
  id: String,
  livemode: Boolean,
  created: Long,
  used: Boolean,
  `type`: String,
  card: Option[Card],
  raw: Option[JValue] = None
) extends StripeObject

object Token extends Gettable[Token] {
  def baseResourceCalculator(req: Req) = req / "tokens"

  def createForBankAccount(
    country: String,
    routingNumber: String,
    accountNumber: String
  )(implicit exec: StripeExecutor) = {
    exec.executeFor[Token](baseResourceCalculator(exec.baseReq) << Map(
      "bank_account[country]" -> country,
      "bank_account[routing_number]" -> routingNumber,
      "bank_account[account_number]" -> accountNumber
    ))
  }

  def createForCard(
    number: String,
    expMonth: Int,
    expYear: Int,
    cvc: String,
    name: Option[String] = None,
    addressLine1: Option[String] = None,
    addressLine2: Option[String] = None,
    addressCity: Option[String] = None,
    addressZip: Option[String] = None,
    addressState: Option[String] = None,
    addressCountry: Option[String] = None
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "card[number]" -> number,
      "card[exp_month]" -> expMonth.toString,
      "card[exp_year]" -> expYear.toString,
      "card[cvc]" -> cvc
    )

    val optionalParams = List(
      name.map(("card[name]", _)),
      addressLine1.map(("card[address_line1]", _)),
      addressLine2.map(("card[address_line2]", _)),
      addressCity.map(("card[address_city]", _)),
      addressState.map(("card[address_state]", _)),
      addressZip.map(("card[address_zip]", _)),
      addressCountry.map(("card[address_country]", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams

    exec.executeFor[Token](baseResourceCalculator(exec.baseReq) << params)
  }
}
