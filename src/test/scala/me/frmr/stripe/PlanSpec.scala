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
        }
      """

      val testPlan = camelifyFieldNames(parse(examplePlanJson)).extract[Plan]

      testPlan.interval should equal(Some("month"))
      testPlan.name should equal(Some("-JYI_nRwd_ltcGRLnVys"))
      testPlan.created should equal(Some(1412299176))
      testPlan.amount should equal(Some(2000))
      testPlan.currency should equal(Some("usd"))
      testPlan.id should equal(Some("-JYI_nRwd_ltcGRLnVys"))
      testPlan.livemode should equal(Some(false))
      testPlan.intervalCount should equal(Some(1))
      testPlan.trialPeriodDays should equal(None)
      testPlan.statementDescription should equal(None)
    }
  }
}
