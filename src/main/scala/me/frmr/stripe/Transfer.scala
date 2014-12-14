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
  date: Long,
  livemode: Boolean,
  amount: Long,
  currency: String,
  status: String,
  `type`: String,
  balanceTransaction: String,
  description: String,
  failureMessage: Option[String],
  failureCode: Option[String],
  statementDescription: Option[String],
  recipient: Option[String],
  bankAccount: Option[BankAccount],
  card: Option[Card],
  metadata: Map[String, String],
  raw: Option[JValue] = None
) extends StripeObject

object Transfer extends Listable[TransferList] with Gettable[Transfer] {
  def baseResourceCalculator(req: Req) = req / "transfers"

  def create(
    amount: Long,
    currency: String,
    recipient: String,
    description: Option[String] = None,
    bankAccount: Option[String] = None,
    card: Option[String] = None,
    statementDescription: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "amount" -> amount.toString,
      "currency" -> currency
    )

    val optionalParams = List(
      description.map(("description", _)),
      bankAccount.map(("bank_account", _)),
      card.map(("card", _)),
      statementDescription.map(("statement_description", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)

    exec.executeFor[Transfer](baseResourceCalculator(exec.baseReq) << params)
  }

  def update(
    id: String,
    description: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val params = List(description.map(("description", _))).flatten.toMap ++ metadataProcessor(metadata)

    exec.executeFor[Transfer](baseResourceCalculator(exec.baseReq) / id << params)
  }

  def cancel(id: String)(implicit exec: StripeExecutor) = {
    exec.executeFor[Transfer]((baseResourceCalculator(exec.baseReq) / id / "cancel").POST)
  }
}
