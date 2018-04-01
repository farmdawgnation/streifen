package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Transfer(
  id: String,
  created: Long,
  livemode: Boolean,
  amount: Long,
  amountReversed: Long,
  currency: String,
  balanceTransaction: String,
  destination: String,
  metadata: Map[String, String],
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object Transfer extends Listable[TransferList] with Gettable[Transfer] {
  def baseResourceCalculator(req: Req) = req / "transfers"

  def create(
    amount: Long,
    currency: String,
    destination: String,
    statementDescription: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "destination" -> destination,
      "amount" -> amount.toString,
      "currency" -> currency
    )

    val optionalParams = List(
      statementDescription.map(("statement_description", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)

    exec.executeFor[Transfer](baseResourceCalculator(exec.baseReq) << params)
  }

  def update(
    id: String,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {

    exec.executeFor[Transfer](baseResourceCalculator(exec.baseReq) / id << metadata)
  }

  def cancel(id: String)(implicit exec: StripeExecutor) = {
    exec.executeFor[Transfer]((baseResourceCalculator(exec.baseReq) / id / "cancel").POST)
  }
}
