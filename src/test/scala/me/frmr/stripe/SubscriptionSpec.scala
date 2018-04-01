package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class SubscriptionSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Subscription object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleSubscriptionJson = """
        {
          "id": "sub_89hyIohmkV31Ew",
          "object": "subscription",
          "application_fee_percent": null,
          "cancel_at_period_end": false,
          "canceled_at": null,
          "current_period_end": 1461704593,
          "current_period_start": 1459026193,
          "billing_cycle_anchor": "now",
          "customer": "cus_89c8SrPpHZP4ex",
          "discount": null,
          "ended_at": null,
          "items": {
            "object": "list",
            "data": [
              {
                "id": "si_18Tken2r9wsOBpY08G3mj9qS",
                "object": "subscription_item",
                "created": 1459217735,
                "metadata": {
                },
                "plan": {
                  "id": "product",
                  "object": "plan",
                  "amount": 1000,
                  "billing_scheme": "per_unit",
                  "created": 1459217641,
                  "currency": "usd",
                  "interval": "month",
                  "interval_count": 1,
                  "livemode": false,
                  "metadata": {
                },
                "nickname": null,
                "product": "prod_BTpMHa2qF5LEXP",
                "tiers": null,
                "tiers_mode": null,
                "transform_usage": null,
                "trial_period_days": null,
                "usage_type": "licensed",
                "statement_descriptor": "Company ABC",
                "name": "Product"
              },
              "quantity": 1,
              "subscription": "sub_8AXTyitxcR9LM6"
            }],
            "has_more": false,
            "total_count": 1,
            "url": "/v1/subscription_items?subscription=sub_8AXTyitxcR9LM6"
          },
          "metadata": {
          },
          "plan": {
            "id": "gold2132",
            "object": "plan",
            "amount": 2000,
            "created": 1386249594,
            "currency": "usd",
            "interval": "month",
            "interval_count": 1,
            "livemode": false,
            "metadata": {
            },
            "name": "Gold ",
            "statement_descriptor": null,
            "trial_period_days": null
          },
          "quantity": 1,
          "start": 1459026193,
          "status": "active",
          "tax_percent": 0.08,
          "trial_end": null,
          "trial_start": null
        }
      """

      val testSubscription = camelifyFieldNames(parse(exampleSubscriptionJson)).extract[Subscription]

      testSubscription.id should equal(Some("sub_89hyIohmkV31Ew"))
      testSubscription.start should equal(Some(1459026193))
      testSubscription.status should equal(Some("active"))
      testSubscription.customer should equal(Some("cus_89c8SrPpHZP4ex"))
      testSubscription.cancelAtPeriodEnd should equal(Some(false))
      testSubscription.currentPeriodStart should equal(Some(1459026193))
      testSubscription.currentPeriodEnd should equal(Some(1461704593))
      testSubscription.billingCycleAnchor should equal(Some("now"))
      testSubscription.endedAt should equal(None)
      testSubscription.trialStart should equal(None)
      testSubscription.trialEnd should equal(None)
      testSubscription.canceledAt should equal(None)
      testSubscription.quantity should equal(Some(1))
      testSubscription.applicationFeePercent should equal(None)
      testSubscription.taxPercent should equal(Some(0.08))
      testSubscription.discount should equal(None)
    }
  }
}
