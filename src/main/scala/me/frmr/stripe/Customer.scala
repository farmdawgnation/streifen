package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

/**
 * Implementation of the Customer data structure in
 * Stripe's API. See https://stripe.com/docs/api#customer_object
**/
class Customer(underlyingData: JValue) extends StripeObject[Customer](underlyingData) {
  def id = stringValueFor(_ \ "id")
  def created = longValueFor(_ \ "created")
  def accountBalance = intValueFor(_ \ "account_balance")
  def currency = stringValueFor(_ \ "currency")
  def defaultCard = stringValueFor(_ \ "default_card")
  def delinquent = booleanValueFor(_ \ "delinquent")
  def description = stringValueFor(_ \ "description")
  def email = stringValueFor(_ \ "email")
  def metadata = mapValueFor(_ \ "metadata")
}

/**
 * Helper object for working with Customers.
**/
object Customer {
  def apply(
    id: Option[String] = None,
    /* cards: List[Card] = Nil */
    created: Option[Long] = None,
    accountBalance: Option[Int] = None,
    currency: Option[String] = None,
    defaultCard: Option[String] = None,
    delinquent: Option[String] = None,
    description: Option[String] = None,
    /* discount: Option[Discount] = None */
    email: Option[String] = None,
    metadata: Map[String, String] = Map.empty
    /* subscriptions: List[Subscription] */
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
      ("metadata" -> metadata)
    )
  }
}
