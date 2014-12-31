package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Coupon(
  id: String,
  livemode: Boolean,
  created: Long,
  duration: String,
  amountOff: Option[Long],
  currency: Option[String],
  durationInMonths: Option[Int],
  maxRedemptions: Option[Int],
  percentOff: Option[Int],
  redeemBy: Option[Long],
  timesRedeemed: Int,
  valid: Boolean,
  metadata: Map[String, String],
  raw: Option[JValue] = None
) extends StripeObject

object Coupon extends Listable[CouponList] with Gettable[Coupon] with Deleteable {
  def baseResourceCalculator(req: Req) =
    req / "coupons"

  def create(
    duration: String,
    id: Option[String] = None,
    amountOff: Option[Long] = None,
    currency: Option[String] = None,
    durationInMonths: Option[Int] = None,
    maxRedemptions: Option[Int] = None,
    percentOff: Option[Int] = None,
    redeemBy: Option[Long] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map("duration" -> duration)

    val optionalParams = List(
      id.map(("id", _)),
      amountOff.map(a => ("amount_off", a.toString)),
      currency.map(c => ("currency", c.toString)),
      durationInMonths.map(d => ("duration_in_months", d.toString)),
      maxRedemptions.map(m => ("max_redemptions", m.toString)),
      percentOff.map(p => ("percent_off", p.toString)),
      redeemBy.map(r => ("redeem_by", r.toString))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq)

    exec.executeFor[Coupon](uri << params)
  }

  def update(
    id: String,
    metadata: Map[String, String]
  )(implicit exec: StripeExecutor) = {
    val params = metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq) / id

    exec.executeFor[Coupon](uri << params)
  }
}
