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
          "id": "sub_56Eev7i0d0N9Dx",
          "plan": {
            "interval": "month",
            "name": "-JYI_nRwd_ltcGRLnVys",
            "created": 1412299176,
            "amount": 2000,
            "currency": "usd",
            "id": "-JYI_nRwd_ltcGRLnVys",
            "object": "plan",
            "livemode": false,
            "interval_count": 1,
            "trial_period_days": null,
            "metadata": {
            },
            "statement_description": null
          },
          "object": "subscription",
          "start": 1415326152,
          "status": "active",
          "customer": "cus_56ENenCV0GVVQ6",
          "cancel_at_period_end": false,
          "current_period_start": 1415326152,
          "current_period_end": 1417918152,
          "ended_at": null,
          "trial_start": null,
          "trial_end": null,
          "canceled_at": null,
          "quantity": 1,
          "application_fee_percent": null,
          "discount": null,
          "metadata": {
          }
        }
      """

      val testSubscription = camelifyFieldNames(parse(exampleSubscriptionJson)).extract[Subscription]

      testSubscription.id should equal(Some("sub_56Eev7i0d0N9Dx"))
      testSubscription.start should equal(Some(1415326152))
      testSubscription.status should equal(Some("active"))
      testSubscription.customer should equal(Some("cus_56ENenCV0GVVQ6"))
      testSubscription.cancelAtPeriodEnd should equal(Some(false))
      testSubscription.currentPeriodStart should equal(Some(1415326152))
      testSubscription.currentPeriodEnd should equal(Some(1417918152))
      testSubscription.endedAt should equal(None)
      testSubscription.trialStart should equal(None)
      testSubscription.trialEnd should equal(None)
      testSubscription.canceledAt should equal(None)
      testSubscription.quantity should equal(Some(1))
      testSubscription.applicationFeePercent should equal(None)
      testSubscription.discount should equal(None)
    }
  }
}
