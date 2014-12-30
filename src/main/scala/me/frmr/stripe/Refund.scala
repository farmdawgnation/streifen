package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Refund(
  id: String,
  amount: Long,
  created: Long,
  currency: String,
  balanceTransaction: String,
  charge: String,
  metadata: Map[String, String],
  reason: Option[String],
  receiptNumber: Option[String],
  description: Option[String],
  raw: Option[JValue]
) extends StripeObject

object Refund extends ChildListable[RefundList] with ChildGettable[Refund] {
  def baseResourceCalculator(req: Req, parentId: String) =
    req / "charges" / parentId / "refunds"

  def create(
    chargeId: String,
    amount: Option[Long] = None,
    refundApplicationFee: Option[Boolean] = None,
    reason: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val optionalParams = List(
      amount.map(a => ("amount", a.toString)),
      refundApplicationFee.map(r => ("refund_application_fee", r.toString)),
      reason.map(("reason", _))
    ).flatten.toMap

    val params = optionalParams ++ metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq, chargeId)

    exec.executeFor[Refund](uri << params)
  }

  def update(
    chargeId: String,
    refundId: String,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val params = metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq, chargeId) / refundId

    exec.executeFor[Refund](uri << params)
  }
}
