package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Discount(
  coupon: Coupon,
  customer: String,
  start: Long,
  end: Long,
  subscription: String,
  raw: Option[JValue] = None
) extends StripeObject
