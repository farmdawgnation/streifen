package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class SubscriptionItemSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "SubscriptionItem object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleSubscriptionItemJson = """
        {
          "id": "si_CaebcaMq5Zi6pT",
          "object": "subscription_item",
          "created": 1522439625,
          "metadata": {
          },
          "plan": {
            "id": "1898406931789832433",
            "object": "plan",
            "amount": 1100,
            "billing_scheme": "per_unit",
            "created": 1496696916,
            "currency": "usd",
            "interval": "month",
            "interval_count": 1,
            "livemode": false,
            "metadata": {
            },
            "nickname": null,
            "product": "prod_BTc8VlKsaC0JLd",
            "tiers": null,
            "tiers_mode": null,
            "transform_usage": null,
            "trial_period_days": null,
            "usage_type": "licensed",
            "statement_descriptor": null,
            "name": "Gold Item"
          },
          "quantity": 1,
          "subscription": "sub_Caebh1PAe5c8sr"
        }
      """

      val testSubscriptionItem = camelifyFieldNames(parse(exampleSubscriptionItemJson)).extract[SubscriptionItem]

      testSubscriptionItem.id should equal(Some("si_CaebcaMq5Zi6pT"))
      testSubscriptionItem.subscription should equal(Some("sub_Caebh1PAe5c8sr"))
      testSubscriptionItem.quantity should equal(Some(1))
    }
  }
}
