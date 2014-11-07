package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

case class Subscription(
  id: Option[String],
  start: Option[Long],
  status: Option[String],
  customer: Option[String],
  cancelAtPeriodEnd: Option[Boolean],
  currentPeriodStart: Option[Long],
  currentPeriodEnd: Option[Long],
  endedAt: Option[Long],
  trialStart: Option[Long],
  trialEnd: Option[Long],
  canceledAt: Option[Long],
  quantity: Option[Int],
  applicationFeePercent: Option[Int],
  discount: Option[Discount],
  raw: Option[JValue]
) extends StripeObject
