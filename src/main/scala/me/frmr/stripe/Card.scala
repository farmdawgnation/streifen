package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

/**
 * Implementation of the Charge data structure in
 * Stripe's API. See https://stripe.com/docs/api#card_object
**/
case class Card(
  id: String,
  brand: String,
  expMonth: Int,
  expYear: Int,
  fingerprint: String,
  funding: String,
  last4: String,
  addressCity: Option[String] = None,
  addressCountry: Option[String] = None,
  addressLine1: Option[String] = None,
  addressLine1Check: Option[String] = None,
  addressLine2: Option[String] = None,
  addressState: Option[String] = None,
  addressZip: Option[String] = None,
  addressZipCheck: Option[String] = None,
  country: Option[String] = None,
  customer: Option[String] = None,
  cvcCheck: Option[String] = None,
  dynamicLast4: Option[String] = None,
  name: Option[String] = None,
  receipient: Option[String] = None,
  raw: Option[JValue] = None
) extends StripeObject

object Card extends ChildListable[CardList] with ChildGettable[Card] with ChildDeleteable {
  def baseResourceCalculator(req: Req, parentId: String) =
    req / "customers" / parentId / "cards"

  def create(customerId: String, card: String)(implicit exec: StripeExecutor) = {
    val uri = baseResourceCalculator(exec.baseReq, customerId)
    val params = Map("card" -> card)

    exec.executeFor[Card](uri << params)
  }

  def create(
    customerId: String,
    number: String,
    expMonth: Int,
    expYear: Int,
    cvc: Option[String] = None,
    name: Option[String] = None,
    addressLine1: Option[String] = None,
    addressLine2: Option[String] = None,
    addressCity: Option[String] = None,
    addressZip: Option[String] = None,
    addressState: Option[String] = None,
    addressCountry: Option[String] = None
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "number" -> number,
      "exp_month" -> expMonth.toString,
      "exp_year" -> expYear.toString
    )
    val optionalParams = List(
      cvc.map(("cvc", _)),
      name.map(("name", _)),
      addressLine1.map(("address_line1", _)),
      addressLine2.map(("address_line2", _)),
      addressCity.map(("address_city", _)),
      addressZip.map(("address_zip", _)),
      addressState.map(("address_state", _)),
      addressCountry.map(("address_country", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams
    val uri = baseResourceCalculator(exec.baseReq, customerId)

    exec.executeFor[Card](uri << params)
  }

  def update(
    customerId: String,
    cardId: String,
    expMonth: Option[Int] = None,
    expYear: Option[Int] = None,
    name: Option[String] = None,
    addressLine1: Option[String] = None,
    addressLine2: Option[String] = None,
    addressCity: Option[String] = None,
    addressZip: Option[String] = None,
    addressState: Option[String] = None,
    addressCountry: Option[String] = None
  )(implicit exec: StripeExecutor) = {
    val params = List(
      name.map(("name", _)),
      addressLine1.map(("address_line1", _)),
      addressLine2.map(("address_line2", _)),
      addressCity.map(("address_city", _)),
      addressZip.map(("address_zip", _)),
      addressState.map(("address_state", _)),
      addressCountry.map(("address_country", _)),
      expMonth.map(e => ("exp_month", e.toString)),
      expYear.map(e => ("exp_year", e.toString))
    ).flatten.toMap

    val uri = baseResourceCalculator(exec.baseReq, customerId) / cardId

    exec.executeFor[Card](uri << params)
  }
}
