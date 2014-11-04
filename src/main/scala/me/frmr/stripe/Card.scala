package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

/**
 * Implementation of the Charge data structure in
 * Stripe's API. See https://stripe.com/docs/api#card_object
**/
case class Card(
  id: Option[String] = None,
  brand: Option[String] = None,
  expMonth: Option[Int] = None,
  expYear: Option[Int] = None,
  fingerprint: Option[String] = None,
  funding: Option[String] = None,
  last4: Option[String] = None,
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
