package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

case class Plan(
  interval: Option[String],
  name: Option[String],
  created: Option[Long],
  amount: Option[Long],
  currency: Option[String],
  id: Option[String],
  livemode: Option[Boolean],
  intervalCount: Option[Int],
  trialPeriodDays: Option[Int],
  statementDescription: Option[String],
  raw: Option[JValue]
) extends StripeObject
