package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class ApplicationFeeRefund(
  id: String,
  amount: Long,
  created: Long,
  currency: String,
  balanceTransaction: Option[String],
  metadata: Map[String, String],
  fee: String,
  raw: Option[JValue] = None
) extends StripeObject

object ApplicationFeeRefund extends ChildListable[ApplicationFeeRefundList] with ChildGettable[ApplicationFeeRefund] {
  def baseResourceCalculator(req: Req, parentId: String) =
    req / "application_fees" / parentId / "refunds"

  def create(
    id: String,
    amount: Option[Long] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val uri = baseResourceCalculator(exec.baseReq, id)
    val params = List(
      Some(("id", id)),
      amount.map(amt => ("amount", amt.toString))
    ).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[ApplicationFeeRefund](uri << params)
  }

  def update(
    feeId: String,
    refundId: String,
    metadata: Map[String, String]
  )(implicit exec: StripeExecutor) = {
    val uri = baseResourceCalculator(exec.baseReq, feeId) / refundId
    val params = metadataProcessor(metadata)

    exec.executeFor[ApplicationFeeRefund](uri << params)
  }
}
