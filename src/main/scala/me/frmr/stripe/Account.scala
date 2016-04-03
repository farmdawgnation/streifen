package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Account(
  id: String,
  chargesEnabled: Boolean,
  country: String,
  defaultCurrency: String,
  detailsSubmitted: Boolean,
  transfersEnabled: Boolean,
  displayName: String,
  statementDescriptor: Option[String],
  timezone: String,
  email: String,
  businessName: String,
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object Account {
  def get(implicit exec: StripeExecutor) =
    exec.executeFor[Account](exec.baseReq / "account")
}
