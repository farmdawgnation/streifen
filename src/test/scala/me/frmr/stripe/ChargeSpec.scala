package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class ChargeSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "Charge object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleChargeJson = """
        {
          "id": "ch_17tNoH2eZvKYlo2CBtLgE7Wa",
          "object": "charge",
          "amount": 300,
          "amount_refunded": 0,
          "application_fee": null,
          "balance_transaction": "txn_17bBwe2eZvKYlo2Cuwcyi9or",
          "captured": true,
          "created": 1459023301,
          "currency": "usd",
          "customer": "cus_7yobPWbyKiMd1K",
          "description": null,
          "destination": null,
          "dispute": null,
          "failure_code": null,
          "failure_message": null,
          "fraud_details": {
          },
          "invoice": "in_17tMpm2eZvKYlo2CVXsIe10j",
          "livemode": false,
          "metadata": {
          },
          "order": null,
          "paid": true,
          "receipt_email": null,
          "receipt_number": null,
          "refunded": false,
          "refunds": {
            "object": "list",
            "data": [

            ],
            "has_more": false,
            "total_count": 0,
            "url": "/v1/charges/ch_17tNoH2eZvKYlo2CBtLgE7Wa/refunds"
          },
          "shipping": null,
          "source": {
            "id": "card_17iqyX2eZvKYlo2CTS5vgxM4",
            "object": "card",
            "address_city": null,
            "address_country": null,
            "address_line1": null,
            "address_line1_check": null,
            "address_line2": null,
            "address_state": null,
            "address_zip": null,
            "address_zip_check": null,
            "brand": "Visa",
            "country": "US",
            "customer": "cus_7yobPWbyKiMd1K",
            "cvc_check": null,
            "dynamic_last4": null,
            "exp_month": 12,
            "exp_year": 2016,
            "funding": "credit",
            "last4": "4242",
            "metadata": {
            },
            "name": null,
            "tokenization_method": null
          },
          "source_transfer": null,
          "statement_descriptor": null,
          "status": "succeeded"
        }
      """

      val testCharge = camelifyFieldNames(parse(exampleChargeJson)).extract[Charge]

      testCharge.id.get should equal("ch_17tNoH2eZvKYlo2CBtLgE7Wa")
      testCharge.created.get should equal(1459023301)
      testCharge.livemode.get should equal(false)
      testCharge.paid.get should equal(true)
      testCharge.amount.get should equal(300)
      testCharge.currency.get should equal("usd")
      testCharge.refunded.get should equal(false)
      testCharge.captured.get should equal(true)
      testCharge.balanceTransaction.get should equal("txn_17bBwe2eZvKYlo2Cuwcyi9or")
      testCharge.failureMessage should equal(None)
      testCharge.failureCode should equal(None)
      testCharge.amountRefunded.get should equal(0)
      testCharge.customer should equal(Some("cus_7yobPWbyKiMd1K"))
    }
  }
}
