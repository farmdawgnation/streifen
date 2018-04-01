package me.frmr.stripe 

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Payout(
  id: String,
  amount: Long,
  arrivalDate: Long,
  automatic: Boolean,
  balanceTransaction: String,
  created: Long,
  currency: String,
  description: String,
  failureBalanceTransaction: Option[String],
  failureCode: Option[String],
  livemode: Boolean,
  method: String,
  sourceType: String,
  statementDescriptor: String,
  status: String,
  `type`: String,
  metadata: Map[String, String],
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object Payout extends Listable[PayoutList] with Gettable[Payout] {
  def baseResourceCalculator(req: Req) = req / "payouts"

  def create(
    amount: Long,
    currency: String,
    description: Option[String] = None,
    destination: Option[String] = None,
    method: Option[String] = None,
    sourceType: Option[String] = None,
    statementDescriptor: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "amount" -> amount.toString,
      "currency" -> currency
    )

    val optionalParams = List(
      description.map(("description", _)),
      destination.map(("destination", _)),
      method.map(("method", _)),
      statementDescriptor.map(("statement_description", _)),
      sourceType.map(("source_type", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)

    exec.executeFor[Payout](baseResourceCalculator(exec.baseReq) << params)
  }

  def update(
    id: String,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    exec.executeFor[Payout](baseResourceCalculator(exec.baseReq) / id << metadata)
  }

  def cancel(id: String)(implicit exec: StripeExecutor) = {
    exec.executeFor[Payout]((baseResourceCalculator(exec.baseReq) / id / "cancel").POST)
  }
}

