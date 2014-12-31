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
          "interval": "month",
          "name": "AAAAplanname",
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
          "statement_descriptor": null
        }
      """

      val testPlan = camelifyFieldNames(parse(examplePlanJson)).extract[Plan]

      testPlan.interval should equal("month")
      testPlan.name should equal("AAAAplanname")
      testPlan.created should equal(1412299176)
      testPlan.amount should equal(2000)
      testPlan.currency should equal("usd")
      testPlan.id should equal("-JYI_nRwd_ltcGRLnVys")
      testPlan.livemode should equal(false)
      testPlan.intervalCount should equal(1)
      testPlan.trialPeriodDays should equal(None)
      testPlan.statementDescriptor should equal(None)
    }
  }
}
