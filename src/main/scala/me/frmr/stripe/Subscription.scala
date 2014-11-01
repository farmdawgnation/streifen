package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

class Subscription(underlyingData: JValue) extends StripeObject(underlyingData)
