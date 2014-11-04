package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

/**
 * Implementation of the Customer data structure in
 * Stripe's API. See https://stripe.com/docs/api#customer_object
**/
case class Customer(
  id: Option[String] = None,
  livemode: Option[Boolean] = None,
  cards: Option[CardList] = None,
  created: Option[Long] = None,
  accountBalance: Option[Int] = None,
  currency: Option[String] = None,
  defaultCard: Option[String] = None,
  delinquent: Option[Boolean] = None,
  description: Option[String] = None,
  discount: Option[Discount] = None,
  email: Option[String] = None,
  metadata: Map[String, String] = Map.empty,
  subscriptions: Option[SubscriptionList] = None,
  raw: Option[JValue] = None
)
