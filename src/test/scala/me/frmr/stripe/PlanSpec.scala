package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class PlanSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "Plan object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val examplePlanJson = """
        {
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
        }
      """

      val testPlan = camelifyFieldNames(parse(examplePlanJson)).extract[Plan]

      testPlan.interval should equal("month")
      testPlan.name should equal("Gold ")
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
