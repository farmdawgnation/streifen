package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

/**
 * Implementation of the Charge data structure in
 * Stripe's API. See https://stripe.com/docs/api#card_object
**/
case class Card(
  id: String,
  brand: String,
  expMonth: Int,
  expYear: Int,
  fingerprint: String,
  funding: String,
  last4: String,
  addressCity: Option[String] = None,
  addressCountry: Option[String] = None,
  addressLine1: Option[String] = None,
  addressLine1Check: Option[String] = None,
  addressLine2: Option[String] = None,
  addressState: Option[String] = None,
  addressZip: Option[String] = None,
  addressZipCheck: Option[String] = None,
  country: Option[String] = None,
  customer: Option[String] = None,
  cvcCheck: Option[String] = None,
  dynamicLast4: Option[String] = None,
  name: Option[String] = None,
  receipient: Option[String] = None,
  raw: Option[JValue] = None
) extends StripeObject
