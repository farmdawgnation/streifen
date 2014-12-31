package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class EventSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Event object" should {
    "retrieve correct fiels from Stripe's JSON" in {
      val exampleEventJson = """
        {
          "id": "evt_15FuBx2eZvKYlo2CWUhJSwAA",
          "created": 1420061985,
          "livemode": false,
          "type": "charge.succeeded",
          "data": {
            "object": {
              "id": "ch_15FuBx2eZvKYlo2CCvbr8nhA",
              "object": "charge",
              "created": 1420061985,
              "livemode": false,
              "paid": true,
              "amount": 100,
              "currency": "usd",
              "refunded": false,
              "captured": true,
              "card": {
                "id": "card_15FuBu2eZvKYlo2CuP3VJSLl",
                "object": "card",
                "last4": "4242",
                "brand": "Visa",
                "funding": "credit",
                "exp_month": 12,
                "exp_year": 2016,
                "fingerprint": "Xt5EWLLDS7FJjR1c",
                "country": "US",
                "name": "sbvmqa+606060@gmail.com",
                "address_line1": null,
                "address_line2": null,
                "address_city": null,
                "address_state": null,
                "address_zip": null,
                "address_country": null,
                "cvc_check": null,
                "address_line1_check": null,
                "address_zip_check": null,
                "dynamic_last4": null,
                "customer": "cus_5QljAQau5W1doA"
              },
              "balance_transaction": "txn_15FuBx2eZvKYlo2CK1g4gLF9",
              "failure_message": null,
              "failure_code": null,
              "amount_refunded": 0,
              "customer": "cus_5QljAQau5W1doA",
              "invoice": null,
              "description": "Charge for VirtuMedix consultation",
              "dispute": null,
              "metadata": {
              },
              "statement_descriptor": null,
              "fraud_details": {
              },
              "receipt_email": null,
              "receipt_number": null,
              "shipping": null,
              "refunds": {
                "object": "list",
                "total_count": 0,
                "has_more": false,
                "url": "/v1/charges/ch_15FuBx2eZvKYlo2CCvbr8nhA/refunds",
                "data": [

                ]
              }
            }
          },
          "object": "event",
          "pending_webhooks": 0,
          "request": "iar_5QljgdiWAB2fxv",
          "api_version": "2014-12-22"
        }
      """

      val exampleEvent = camelifyFieldNames(parse(exampleEventJson)).extract[Event]

      exampleEvent.id should equal("evt_15FuBx2eZvKYlo2CWUhJSwAA")
      exampleEvent.created should equal(1420061985)
      exampleEvent.livemode should equal(false)
      exampleEvent.`type` should equal("charge.succeeded")
      exampleEvent.pendingWebhooks should equal(0)
      exampleEvent.request should equal("iar_5QljgdiWAB2fxv")
      exampleEvent.apiVersion should equal("2014-12-22")
    }
  }
}
