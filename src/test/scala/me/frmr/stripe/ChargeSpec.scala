package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class ChargeSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Charge object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleChargeJson = """
        {
          "id": "ch_14uxPs2eZvKYlo2CIDZ45vfW",
          "object": "charge",
          "created": 1415069492,
          "livemode": false,
          "paid": true,
          "amount": 500,
          "currency": "usd",
          "refunded": false,
          "card": {
            "id": "card_14uxPs2eZvKYlo2CzgxT7pbv",
            "object": "card",
            "last4": "4242",
            "brand": "Visa",
            "funding": "credit",
            "exp_month": 1,
            "exp_year": 2050,
            "fingerprint": "Xt5EWLLDS7FJjR1c",
            "country": "US",
            "name": null,
            "address_line1": null,
            "address_line2": null,
            "address_city": null,
            "address_state": null,
            "address_zip": null,
            "address_country": null,
            "cvc_check": "pass",
            "address_line1_check": null,
            "address_zip_check": null,
            "dynamic_last4": null,
            "customer": null
          },
          "captured": true,
          "refunds": {
            "object": "list",
            "total_count": 0,
            "has_more": false,
            "url": "/v1/charges/ch_14uxPs2eZvKYlo2CIDZ45vfW/refunds",
            "data": [

            ]
          },
          "balance_transaction": "txn_14smii2eZvKYlo2C2XeEZU0O",
          "failure_message": null,
          "failure_code": null,
          "amount_refunded": 0,
          "customer": null,
          "invoice": null,
          "description": null,
          "dispute": null,
          "metadata": {
          },
          "statement_description": null,
          "fraud_details": {
            "stripe_report": null,
            "user_report": null
          },
          "receipt_email": null,
          "receipt_number": null,
          "shipping": null
        }
      """

      val testCharge = camelifyFieldNames(parse(exampleChargeJson)).extract[Charge]

      testCharge.id.get should equal("ch_14uxPs2eZvKYlo2CIDZ45vfW")
      testCharge.created.get should equal(1415069492)
      testCharge.livemode.get should equal(false)
      testCharge.paid.get should equal(true)
      testCharge.amount.get should equal(500)
      testCharge.currency.get should equal("usd")
      testCharge.refunded.get should equal(false)
      testCharge.captured.get should equal(true)
      testCharge.balanceTransaction.get should equal("txn_14smii2eZvKYlo2C2XeEZU0O")
      testCharge.failureMessage should equal(None)
      testCharge.failureCode should equal(None)
      testCharge.amountRefunded.get should equal(0)
      testCharge.customer should equal(None)
    }
  }
}
