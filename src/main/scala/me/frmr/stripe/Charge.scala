package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

case class ChargeShippingAddress(line1: String, city: String, country: String, line2: String,
                                  postalCode: String, state: String)
case class ChargeShipping(address: ChargeShippingAddress, name: String, phone: String)

/**
 * Implementation of the Charge data structure in
 * Stripe's API. See https://stripe.com/docs/api#charge_object
**/
class Charge(underlyingData: JValue) extends StripeObject(underlyingData) {
  def id = stringValueFor(_ \ "id")
  def livemode = booleanValueFor(_ \ "livemode")
  def amount = longValueFor(_ \ "amount")
  def captured = booleanValueFor(_ \ "captured")
  def card = valueFor[Card](_ \ "card")
  def created = longValueFor(_ \ "created")
  def currency = stringValueFor(_ \ "currency")
  def paid = booleanValueFor(_ \ "paid")
  def refunded = booleanValueFor(_ \ "refunded")
  def refunds = valueFor[StripeList[Refund]](_ \ "refunds")
  def amountRefunded = longValueFor(_ \ "amount_refunded")
  def balanceTransaction = stringValueFor(_ \ "balance_transaction")
  def customer = stringValueFor(_ \ "customer")
  def description = stringValueFor(_ \ "description")
  def dispute = valueFor[Dispute](_ \ "dispute")
  def failureCode = stringValueFor(_ \ "failure_code")
  def failureMessage = stringValueFor(_ \ "failure_message")
  def invoice = stringValueFor(_ \ "invoice")
  def metadata = mapValueFor(_ \ "metadata")
  def receiptEmail = stringValueFor(_ \ "receipt_email")
  def receiptNumber = stringValueFor(_ \ "receipt_number")
  def shipping = valueFor[ChargeShipping](_ \ "shipping")
  def statementDescription = stringValueFor(_ \ "statement_description")
  def applicationFee = longValueFor(_ \ "application_fee")
}

/**
 * Helper object for working with charges.
**/
object Charge {
  implicit val formats = DefaultFormats

  def apply(
    amount: Long,
    currency: String,
    customer: Option[String],
    card: Option[String],
    description: Option[String],
    metadata: Map[String, String] = Map.empty,
    capture: Option[Boolean] = None,
    statementDescription: Option[String] = None,
    receiptEmail: Option[String] = None,
    applicationFee: Option[Long] = None,
    shipping: Option[ChargeShipping] = None,
    id: Option[String] = None
  ) = {
    new Charge(
      ("amount" -> amount) ~
      ("currency" -> currency) ~
      ("customer" -> customer) ~
      ("card" -> card) ~
      ("description" -> description) ~
      ("metadata" -> metadata) ~
      ("capture" -> capture) ~
      ("statement_description" -> statementDescription) ~
      ("receipt_email" -> receiptEmail) ~
      ("application_fee" -> applicationFee) ~
      ("shipping" -> decompose(shipping)) ~
      ("id" -> id)
    )
  }
}
