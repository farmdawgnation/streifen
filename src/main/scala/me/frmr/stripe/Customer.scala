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
  id: String,
  livemode: Boolean,
  sources: CardList,
  created: Long,
  accountBalance: Long,
  currency: String,
  delinquent: Boolean,
  defaultSource: Option[String],
  description: Option[String],
  discount: Option[Discount],
  email: Option[String],
  metadata: Map[String, String],
  subscriptions: Option[SubscriptionList],
  taxPercent: Option[Double],
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object Customer extends Listable[CustomerList] with Gettable[Customer] with Deleteable {
  def baseResourceCalculator(req: Req) = req / "customers"

  def create(
    accountBalance: Option[String] = None,
    card: Option[String] = None,
    coupon: Option[String] = None,
    description: Option[String] = None,
    email: Option[String] = None,
    metadata: Map[String, String] = Map.empty,
    plan: Option[String] = None,
    quantity: Option[Int] = None,
    trialEnd: Option[Long] = None,
    taxPercent: Option[Double] = None
  )(implicit exec: StripeExecutor): Future[Box[Customer]] = {
    val params = List(
      accountBalance.map(("account_balance", _)),
      card.map(("card", _)),
      coupon.map(("coupon", _)),
      description.map(("description", _)),
      email.map(("email", _)),
      plan.map(("plan", _)),
      quantity.map(quantity => ("quantity", quantity.toString)),
      trialEnd.map(trialEnd => ("trialEnd", trialEnd.toString)),
      taxPercent.map(taxPercent => ("tax_percent", taxPercent.toString))
    ).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[Customer](baseResourceCalculator(exec.baseReq) << params)
  }

  def update(
    id: String,
    accountBalance: Option[String] = None,
    card: Option[String] = None,
    defaultSource: Option[String] = None,
    coupon: Option[String] = None,
    description: Option[String] = None,
    email: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor): Future[Box[Customer]] = {
    val params = List(
      accountBalance.map(("account_balance", _)),
      card.map(("card", _)),
      coupon.map(("coupon", _)),
      defaultSource.map(("default_source", _)),
      description.map(("description", _)),
      email.map(("email", _))
    ).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[Customer](baseResourceCalculator(exec.baseReq) / id << params)
  }

  def deleteDiscount(customerId: String)(implicit exec: StripeExecutor) = {
    val deleteReq = (baseResourceCalculator(exec.baseReq) / customerId / "discount").DELETE
    exec.executeFor[DeleteResponse](deleteReq)
  }
}
