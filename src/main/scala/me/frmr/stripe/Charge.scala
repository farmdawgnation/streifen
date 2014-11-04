package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

case class ChargeShippingAddress(line1: String, city: String, country: String, line2: String,
                                  postalCode: String, state: String)
case class ChargeShipping(address: ChargeShippingAddress, name: String, phone: String)

/**
 * Implementation of the Charge data structure in
 * Stripe's API. See https://stripe.com/docs/api#charge_object
**/
case class Charge(
  id: Option[String] = None,
  livemode: Option[Boolean] = None,
  amount: Option[Int] = None,
  captured: Option[Boolean] = None,
  card: Option[Card] = None,
  created: Option[Long] = None,
  currency: Option[String] = None,
  paid: Option[Boolean] = None,
  refunded: Option[Boolean] = None,
  refunds: Option[RefundList] = None,
  amountRefunded: Option[Int] = None,
  balanceTransaction: Option[String] = None,
  customer: Option[String] = None,
  description: Option[String] = None,
  dispute: Option[Dispute] = None,
  failureCode: Option[String] = None,
  failureMessage: Option[String] = None,
  invoice: Option[String] = None,
  metadata: Map[String, String] = Map.empty,
  receiptEmail: Option[String] = None,
  receiptNumber: Option[String] = None,
  shipping: Option[ChargeShipping] = None,
  statementDescription: Option[String] = None,
  raw: Option[JValue] = None
) extends StripeObject

