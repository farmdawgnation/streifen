package me.frmr.stripe 

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class PlanSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Plan object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val examplePlanJson = """
        {
          "id": "gold2132",
          "object": "plan",
          "amount": 2000,
          "billing_scheme": "per_unit",
          "created": 1386249594,
          "currency": "usd",
          "interval": "month",
          "interval_count": 1,
          "livemode": false,
          "metadata": {
          },
          "nickname": "Platinum starter",
          "product": "prod_BT1t06tZ3jBCHi",
          "tiers": null,
          "tiers_mode": null,
          "transform_usage": null,
          "trial_period_days": null,
          "usage_type": "licensed"
        }
      """

      val testPlan = camelifyFieldNames(parse(examplePlanJson)).extract[Plan]

      testPlan.interval should equal("month")
      testPlan.created should equal(1386249594)
      testPlan.amount should equal(2000)
      testPlan.currency should equal("usd")
      testPlan.id should equal("gold2132")
      testPlan.livemode should equal(false)
      testPlan.intervalCount should equal(1)
      testPlan.trialPeriodDays should equal(None)
      testPlan.statementDescriptor should equal(None)
    }
  }
}
