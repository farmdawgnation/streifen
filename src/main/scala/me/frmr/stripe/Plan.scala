package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

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
