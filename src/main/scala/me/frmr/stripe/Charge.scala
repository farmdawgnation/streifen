package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

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

object Charge extends Listable[ChargeList] with Gettable[Charge] {
  def baseResourceCalculator(req: Req) = req / "charges"

  def create(
    amount: Long,
    currency: String,
    customer: Option[String] = None,
    card: Option[String] = None,
    description: Option[String] = None,
    capture: Option[Boolean] = None,
    statementDescription: Option[String] = None,
    receiptEmail: Option[String] = None,
    applicationFee: Option[Long] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    (customer, card) match {
      case (None, None) =>
        Failure("Either card or customer must be specified when creating a charge.")

      case _ =>
        val requiredParams = Map(
          "amount" -> amount.toString,
          "currency" -> currency
        )

        val optionalParams = List(
          customer.map(("customer", _)),
          card.map(("card", _)),
          description.map(("description", _)),
          capture.map(c => ("capture", c.toString)),
          statementDescription.map(("statement_description", _)),
          receiptEmail.map(("receipt_email", _)),
          applicationFee.map(a => ("application_fee", a.toString))
        ).flatten.toMap

        val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)

        exec.executeFor[Charge](baseResourceCalculator(exec.baseReq) << params)
    }
  }

  def update(
    id: String,
    description: Option[String] = None,
    fraudDetails: Map[String, String] = Map.empty,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val params =
      List(description.map(("description", _))).flatten.toMap ++
      metadataProcessor(metadata) ++
      fraudDetails.map({
        case (key, value) =>
          (s"fraud_details[$key]", value)
      })

    exec.executeFor[Charge](baseResourceCalculator(exec.baseReq) / id << params)
  }

  def capture(
    id: String,
    amount: Option[Long] = None,
    applicationFee: Option[Long] = None,
    receiptEmail: Option[String] = None
  )(implicit exec: StripeExecutor) = {
    val params = List(
      amount.map(a => ("amount", a.toString)),
      applicationFee.map(a => ("application_fee", a.toString)),
      receiptEmail.map(("receipt_email", _))
    ).flatten.toMap

    exec.executeFor[Charge](baseResourceCalculator(exec.baseReq) / id / "capture" << params)
  }
}
