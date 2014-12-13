package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

/**
 * Implementation of the Token structure in Stripe's API.
 * See https://stripe.com/docs/api#token_object
**/
case class Token(
  id: Option[String] = None,
  livemode: Option[Boolean] = None,
  created: Option[Long] = None,
  used: Option[Boolean] = None,
  `type`: Option[String] = None,
  card: Option[Card] = None,
  raw: Option[JValue] = None
) extends StripeObject
