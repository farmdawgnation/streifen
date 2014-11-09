package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

import dispatch._

case class Subscription(
  id: Option[String],
  start: Option[Long],
  status: Option[String],
  customer: Option[String],
  cancelAtPeriodEnd: Option[Boolean],
  currentPeriodStart: Option[Long],
  currentPeriodEnd: Option[Long],
  endedAt: Option[Long],
  trialStart: Option[Long],
  trialEnd: Option[Long],
  canceledAt: Option[Long],
  quantity: Option[Int],
  applicationFeePercent: Option[Int],
  discount: Option[Discount],
  raw: Option[JValue]
) extends StripeObject

object Subscription extends ChildListable[SubscriptionList] with ChildGettable[Subscription] {
  def baseResourceCalculator(req: Req, parentId: String) =
    req / "customers" / parentId / "subscriptions"

  def create(
    customerId: String,
    plan: String,
    coupon: Option[String] = None,
    trialEnd: Option[Long] = None,
    card: Option[String] = None,
    quantity: Option[Int] = None,
    applicationFeePercent: Option[Double] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor): Future[Box[Subscription]] = {
    val params = List(
      Some(("plan", plan)),
      coupon.map(("coupon", _)),
      trialEnd.map(trialEnd => ("trialEnd", trialEnd.toString)),
      card.map(("card", _)),
      quantity.map(quantity => ("quantity", quantity.toString)),
      applicationFeePercent.map(fee => ("application_fee_percent", fee.toString))
    ).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[Subscription](baseResourceCalculator(exec.baseReq, customerId) << params)
  }

  def update(
    customerId: String,
    subscriptionId: String,
    prorate: Option[Boolean] = None,
    plan: Option[String] = None,
    coupon: Option[String] = None,
    trialEnd: Option[Long] = None,
    card: Option[String] = None,
    quantity: Option[Int] = None,
    applicationFeePercent: Option[Double] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor): Future[Box[Subscription]] = {
    val params = List(
      prorate.map(prorate => ("prorate", prorate.toString)),
      plan.map(("plan", _)),
      coupon.map(("coupon", _)),
      trialEnd.map(trialEnd => ("trialEnd", trialEnd.toString)),
      card.map(("card", _)),
      quantity.map(quantity => ("quantity", quantity.toString)),
      applicationFeePercent.map(fee => ("application_fee_percent", fee.toString))
    ).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[Subscription](baseResourceCalculator(exec.baseReq, customerId) / subscriptionId << params)
  }
}
