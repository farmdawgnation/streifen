package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

import dispatch._

case class SubscriptionItem(
  id: Option[String],
  plan: Option[Plan],
  quantity: Option[Int],
  subscription: Option[String],
  metadata: Map[String, String],
  raw: Option[JValue]
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object SubscriptionItem extends Listable[SubscriptionItemList] with Gettable[SubscriptionItem] {
  def baseResourceCalculator(req: Req) =
    req / "subscriptionitems"

  def create(
    plan: String,
    subscription: String,
    prorate: Option[Boolean] = None,
    prorationDate: Option[Long] = None,
    quantity: Option[Int] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor): Future[Box[SubscriptionItem]] = {
    val params = List(
      Some(("plan", plan)),
      Some(("subscription", subscription)),
      prorate.map(prorate => ("prorate", prorate.toString)),
      prorationDate.map(prorationDate => ("proration_date", prorationDate.toString)),
      quantity.map(quantity => ("quantity", quantity.toString))
    ).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[SubscriptionItem](baseResourceCalculator(exec.baseReq) << params)
  }

  def update(
    subscriptionItemId: String,
    plan: String,
    subscription: String,
    prorate: Option[Boolean] = None,
    prorationDate: Option[Long] = None,
    quantity: Option[Int] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor): Future[Box[SubscriptionItem]] = {
    val params = List(
      Some(("item", subscriptionItemId)),
      Some(("plan", plan)),
      Some(("subscription", subscription)),
      prorate.map(prorate => ("prorate", prorate.toString)),
      prorationDate.map(prorationDate => ("proration_date", prorationDate.toString)),
      quantity.map(quantity => ("quantity", quantity.toString))
    ).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[SubscriptionItem](baseResourceCalculator(exec.baseReq) / subscriptionItemId << params)
  }

  def delete(
    subscriptionItemId: String,
    prorate: Option[Boolean] = None,
    prorationDate: Option[Long] = None
  )(implicit exec: StripeExecutor) = {
    val params = List(
      prorate.map(prorate => ("prorate", prorate.toString)),
      prorationDate.map(prorationDate => ("proration_date", prorationDate.toString))
    ).flatten.toMap

    val deleteReq = (baseResourceCalculator(exec.baseReq) / subscriptionItemId).DELETE
    exec.executeFor[DeleteResponse](deleteReq << params)
  }
}
