package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

/**
 * Implementation of the Customer data structure in
 * Stripe's API. See https://stripe.com/docs/api#customer_object
**/
class Customer(underlyingData: JValue) extends StripeObject(underlyingData) {
  def id = stringValueFor(_ \ "id")
  def livemode = booleanValueFor(_ \ "livemode")
  def created = longValueFor(_ \ "created")
  def accountBalance = intValueFor(_ \ "account_balance")
  def currency = stringValueFor(_ \ "currency")
  def defaultCard = stringValueFor(_ \ "default_card")
  def delinquent = booleanValueFor(_ \ "delinquent")
  def description = stringValueFor(_ \ "description")
  def email = stringValueFor(_ \ "email")
  def metadata = mapValueFor(_ \ "metadata")

  def cards = valueFor[StripeList[Card]](_ \ "cards")
  def discount = valueFor[Discount](_ \ "discount")
  def subscriptions = valueFor[StripeList[Subscription]](_ \ "subscriptions")
}

/**
 * Helper object for working with Customers.
**/
object Customer {
  implicit val formats = DefaultFormats

  def apply(
    id: Option[String] = None,
    cards: StripeList[Card] = Nil,
    created: Option[Long] = None,
    accountBalance: Option[Int] = None,
    currency: Option[String] = None,
    defaultCard: Option[String] = None,
    delinquent: Option[String] = None,
    description: Option[String] = None,
    discount: Option[Discount] = None,
    email: Option[String] = None,
    metadata: Map[String, String] = Map.empty,
    subscriptions: StripeList[Subscription] = Nil
  ) = {
    new Customer(
      ("id" -> id) ~
      ("created" -> created) ~
      ("account_balance" -> accountBalance) ~
      ("currency" -> currency) ~
      ("default_card" -> defaultCard) ~
      ("delinquent" -> delinquent) ~
      ("description" -> description) ~
      ("email" -> email) ~
      ("metadata" -> metadata) ~
      ("cards" -> decompose(cards)) ~
      ("discount" -> decompose(discount)) ~
      ("subscriptions" -> decompose(subscriptions))
    )
  }
}
