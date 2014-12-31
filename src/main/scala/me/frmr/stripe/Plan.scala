package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Plan(
  interval: String,
  name: String,
  created: Long,
  amount: Long,
  currency: String,
  id: String,
  livemode: Boolean,
  intervalCount: Int,
  trialPeriodDays: Option[Int],
  statementDescriptor: Option[String],
  metadata: Map[String, String],
  raw: Option[JValue]
) extends StripeObject

object Plan extends Listable[PlanList] with Gettable[Plan] with Deleteable {
  def baseResourceCalculator(req: Req) =
    req / "plans"

  def create(
    id: String,
    amount: Long,
    currency: String,
    interval: String,
    name: String,
    intervalCount: Option[Int] = None,
    trialPeriodDays: Option[Int] = None,
    statementDescriptor: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "id" -> id,
      "amount" -> amount.toString,
      "currency" -> currency,
      "interval" -> interval,
      "name" -> name
    )

    val optionalParams = List(
      intervalCount.map(i => ("interval_count", i.toString)),
      trialPeriodDays.map(t => ("trial_period_days", t.toString)),
      statementDescriptor.map(("statement_descriptor", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq)

    exec.executeFor[Plan](uri << params)
  }

  def update(
    id: String,
    name: Option[String] = None,
    metadata: Map[String, String] = Map.empty,
    statementDescriptor: Option[String] = None
  )(implicit exec: StripeExecutor) = {
    val params = List(
      name.map(("name", _)),
      statementDescriptor.map(("statement_descriptor", _))
    ).flatten.toMap ++ metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq) / id

    exec.executeFor[Plan](uri << params)
  }
}
