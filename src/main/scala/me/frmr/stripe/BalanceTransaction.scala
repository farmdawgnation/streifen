package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class FeeDetails(
  amount: Long,
  currency: String,
  `type`: String,
  description: Option[String],
  application: Option[String]
)

case class BalanceTransaction(
  id: String,
  amount: Long,
  currency: String,
  net: Long,
  `type`: String,
  created: Long,
  availableOn: Long,
  status: String,
  fee: Long,
  feeDetails: List[FeeDetails],
  source: String,
  description: Option[String],
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object BalanceTransaction extends Gettable[BalanceTransaction] {
  def baseResourceCalculator(req: Req) = req / "balance" / "history"
}
