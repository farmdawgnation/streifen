package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class BalanceItem(amount: Long, currency: String)
case class Balance(
  livemode: Boolean,
  pending: List[BalanceItem],
  available: List[BalanceItem],
  raw: Option[JValue] = None
) extends StripeObject

object Balance {
  def get(implicit exec: StripeExecutor) =
    exec.executeFor[Balance](exec.baseReq / "balance")
}
