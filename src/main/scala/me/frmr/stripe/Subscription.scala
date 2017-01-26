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
  taxPercent: Option[Double],
  applicationFeePercent: Option[Int],
  discount: Option[Discount],
  raw: Option[JValue]
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

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
    taxPercent: Option[Double] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor): Future[Box[Subscription]] = {
    val params = List(
      Some(("plan", plan)),
      coupon.map(("coupon", _)),
      trialEnd.map(trialEnd => ("trialEnd", trialEnd.toString)),
      card.map(("card", _)),
      quantity.map(quantity => ("quantity", quantity.toString)),
      applicationFeePercent.map(fee => ("application_fee_percent", fee.toString)),
      taxPercent.map(taxPercent => ("tax_percent", taxPercent.toString))
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
    taxPercent: Option[Double] = None,
    applicationFeePercent: Option[Double] = None,
    trialEnd: Option[Double] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor): Future[Box[Subscription]] = {
    val params = List(
      prorate.map(prorate => ("prorate", prorate.toString)),
      plan.map(("plan", _)),
      coupon.map(("coupon", _)),
      trialEnd.map(trialEnd => ("trialEnd", trialEnd.toString)),
      card.map(("card", _)),
      quantity.map(quantity => ("quantity", quantity.toString)),
      applicationFeePercent.map(fee => ("application_fee_percent", fee.toString)),
      taxPercent.map(taxPercent => ("tax_percent", taxPercent.toString)),
      trialEnd.map(trailEnd => ("trial_end", trialEnd.toString))
    ).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[Subscription](
      baseResourceCalculator(exec.baseReq, customerId) / subscriptionId << params
    )
  }

  def cancel(customerId: String, subscriptionId: String, atPeriodEnd: Boolean = false)(implicit exec: StripeExecutor) = {
    val params = Map("at_period_end" -> atPeriodEnd.toString)
    exec.executeFor[Subscription](
      (baseResourceCalculator(exec.baseReq, customerId) / subscriptionId << params).DELETE
    )
  }

  def deleteDiscount(customerId: String, subscriptionId: String)(implicit exec: StripeExecutor) = {
    val deleteReq = (baseResourceCalculator(exec.baseReq, customerId) / subscriptionId).DELETE
    exec.executeFor[DeleteResponse](deleteReq)
  }
}
