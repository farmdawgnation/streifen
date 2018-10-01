package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class EventSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "Event object" should {
    "retrieve correct fiels from Stripe's JSON" in {
      val exampleEventJson = """
        {
          "id": "evt_17tOU02eZvKYlo2C6jfLWVct",
          "object": "event",
          "api_version": "2016-03-07",
          "created": 1459025888,
          "data": {
            "object": {
              "id": "sub_7way2VPy8fnbjp",
              "object": "subscription",
              "application_fee_percent": null,
              "cancel_at_period_end": false,
              "canceled_at": null,
              "current_period_end": 1459630606,
              "current_period_start": 1459025806,
              "customer": "cus_7walp6daH6Hrns",
              "discount": null,
              "ended_at": null,
              "metadata": {
              },
              "plan": {
                "id": "$555.55_subsplan10",
                "object": "plan",
                "amount": 55555,
                "created": 1456001805,
                "currency": "usd",
                "interval": "week",
                "interval_count": 1,
                "livemode": false,
                "metadata": {
                },
                "name": "$555.55 Subscription Plan For Joe Aliling_24",
                "statement_descriptor": null,
                "trial_period_days": null
              },
              "quantity": 1,
              "start": 1456001806,
              "status": "active",
              "tax_percent": null,
              "trial_end": null,
              "trial_start": null
            },
            "previous_attributes": {
              "current_period_end": 1459025806,
              "current_period_start": 1458421006
            }
          },
          "livemode": false,
          "pending_webhooks": 0,
          "request": null,
          "type": "customer.subscription.updated"
        }
      """

      val exampleEvent = camelifyFieldNames(parse(exampleEventJson)).extract[Event]

      exampleEvent.id should equal("evt_17tOU02eZvKYlo2C6jfLWVct")
      exampleEvent.created should equal(1459025888)
      exampleEvent.livemode should equal(false)
      exampleEvent.`type` should equal("customer.subscription.updated")
      exampleEvent.pendingWebhooks should equal(0)
      exampleEvent.request should equal(null)
      exampleEvent.apiVersion should equal("2016-03-07")
    }
  }
}
