package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

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
) extends StripeObject

object Customer extends Listable[CustomerList] with Gettable[Customer] with Deleteable {
  def baseResourceCalculator(req: Req) = req / "customers"

  def create(
    accountBalance: Option[String] = None,
    card: Option[String] = None,
    coupon: Option[String] = None,
    description: Option[String] = None,
    email: Option[String] = None,
    metadata: Option[Map[String, String]] = None,
    plan: Option[String] = None,
    quantity: Option[Int] = None,
    trialEnd: Option[Long] = None
  )(implicit exec: StripeExecutor): Future[Box[Customer]] = {
    val params = List(
      accountBalance.map(("account_balance", _)),
      card.map(("card", _)),
      coupon.map(("coupon", _)),
      description.map(("description", _)),
      email.map(("email", _)),
      //metadata.map(metadata => ("metadata", metadata)),
      plan.map(("plan", _)),
      quantity.map(quantity => ("quantity", quantity.toString)),
      trialEnd.map(trialEnd => ("trialEnd", trialEnd.toString))
    ).flatten.toMap

    exec.executeFor[Customer](baseResourceCalculator(exec.baseReq) << params)
  }
}
