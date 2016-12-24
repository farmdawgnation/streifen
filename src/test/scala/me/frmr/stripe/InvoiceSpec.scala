package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class InvoiceSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Invoice object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val json = """
      {
        "id": "in_19Natl2bG3XBuYiIpVqtN11d",
        "object": "invoice",
        "amount_due": 0,
        "application_fee": null,
        "attempt_count": 0,
        "attempted": false,
        "charge": null,
        "closed": false,
        "currency": "usd",
        "customer": "cus_9gyrZOecXAh5Dz",
        "date": 1480999789,
        "description": null,
        "discount": {
          "object": "discount",
          "coupon": {
            "id": "twopets",
            "object": "coupon",
            "amount_off": null,
            "created": 1480277071,
            "currency": null,
            "duration": "forever",
            "duration_in_months": null,
            "livemode": false,
            "max_redemptions": null,
            "metadata": {},
            "percent_off": 5,
            "redeem_by": null,
            "times_redeemed": 7,
            "valid": true
          },
          "customer": "cus_9UTpVQq30U9bWb",
          "end": null,
          "start": 1482593004,
          "subscription": "sub_9nt9bLaaimRN2I"
        },
        "ending_balance": null,
        "forgiven": false,
        "lines": {
          "data": [
            {
              "id": "sub_9gyrQVakdcI6ji",
              "object": "line_item",
              "amount": 999,
              "currency": "usd",
              "description": null,
              "discountable": true,
              "livemode": true,
              "metadata": {
              },
              "period": {
                "start": 1483678188,
                "end": 1486356588
              },
              "plan": {
                "id": "gold",
                "object": "plan",
                "amount": 2000,
                "created": 1480999789,
                "currency": "usd",
                "interval": "month",
                "interval_count": 1,
                "livemode": false,
                "metadata": {
                },
                "name": "Gold Special",
                "statement_descriptor": null,
                "trial_period_days": null
              },
              "proration": false,
              "quantity": 1,
              "subscription": null,
              "type": "subscription"
            }
          ],
          "total_count": 1,
          "object": "list",
          "url": "/v1/invoices/in_19Natl2bG3XBuYiIpVqtN11d/lines"
        },
        "livemode": false,
        "metadata": {
        },
        "next_payment_attempt": 1481003389,
        "paid": false,
        "period_end": 1480999789,
        "period_start": 1480999789,
        "receipt_number": null,
        "starting_balance": 0,
        "statement_descriptor": null,
        "subscription": null,
        "subtotal": 0,
        "tax": null,
        "tax_percent": null,
        "total": 0,
        "webhooks_delivered_at": null
      }
      """

      val testInvoice = camelifyFieldNames(parse(json)).extract[Invoice]

      testInvoice.amountDue should equal(0)
      testInvoice.currency should equal("usd")
      testInvoice.customer should equal("cus_9gyrZOecXAh5Dz")
      testInvoice.description should equal(None)
      testInvoice.discount should not equal(None)
    }
  }

  "UpcomingInvoice object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val json = """
      {
        "object": "invoice",
        "amount_due": 0,
        "application_fee": null,
        "attempt_count": 0,
        "attempted": false,
        "charge": null,
        "closed": false,
        "currency": "usd",
        "customer": "cus_9gyrZOecXAh5Dz",
        "date": 1480999789,
        "description": null,
        "discount": {
          "object": "discount",
          "coupon": {
            "id": "twopets",
            "object": "coupon",
            "amount_off": null,
            "created": 1480277071,
            "currency": null,
            "duration": "forever",
            "duration_in_months": null,
            "livemode": false,
            "max_redemptions": null,
            "metadata": {},
            "percent_off": 5,
            "redeem_by": null,
            "times_redeemed": 7,
            "valid": true
          },
          "customer": "cus_9UTpVQq30U9bWb",
          "end": null,
          "start": 1482593004,
          "subscription": "sub_9nt9bLaaimRN2I"
        },
        "ending_balance": null,
        "forgiven": false,
        "lines": {
          "object": "list",
          "data": [

          ],
          "has_more": false,
          "total_count": 0,
          "url": "/v1/invoices/in_19Natl2bG3XBuYiIpVqtN11d/lines"
        },
        "livemode": false,
        "metadata": {
        },
        "next_payment_attempt": 1481003389,
        "paid": false,
        "period_end": 1480999789,
        "period_start": 1480999789,
        "receipt_number": null,
        "starting_balance": 0,
        "statement_descriptor": null,
        "subscription": null,
        "subtotal": 0,
        "tax": null,
        "tax_percent": null,
        "total": 0,
        "webhooks_delivered_at": null
      }
      """

      val testUpcoming = camelifyFieldNames(parse(json)).extract[UpcomingInvoice]

      testUpcoming.amountDue should equal(0)
      testUpcoming.currency should equal("usd")
      testUpcoming.customer should equal("cus_9gyrZOecXAh5Dz")
      testUpcoming.description should equal(None)
      testUpcoming.discount should not equal(None)
    }
  }
}
