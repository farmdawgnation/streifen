package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class ApplicationFee(
  id: String,
  created: Long,
  livemode: Boolean,
  amount: Long,
  currency: String,
  refunded: Boolean,
  amountRefunded: Long,
  refunds: RefundList,
  balanceTransaction: String,
  account: String,
  application: String,
  charge: String,
  raw: Option[JValue] = None
) extends StripeObject

object ApplicationFee extends Listable[ApplicationFeeList] with Gettable[ApplicationFee] {
  def baseResourceCalculator(req: Req) = req / "application_fees"
}
