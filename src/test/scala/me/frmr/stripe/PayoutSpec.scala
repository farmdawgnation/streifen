package me.frmr.stripe 

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class PayoutSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "Payout object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val examplePayoutJson = """
        {
          "id": "tr_10340J2eZvKYlo2Cg42HilbB",
          "object": "payout",
          "amount": 5687,
          "arrival_date": 1386374400,
          "automatic": true,
          "balance_transaction": "txn_19XJJ02eZvKYlo2ClwuJ1rbA",
          "created": 1386212146,
          "currency": "usd",
          "description": "STRIPE TRANSFER",
          "destination": null,
          "failure_balance_transaction": null,
          "failure_code": null,
          "failure_message": null,
          "livemode": false,
          "metadata": {
            "order_id": "6735"
          },
          "method": "standard",
          "source_type": "card",
          "statement_descriptor": null,
          "status": "paid",
          "type": "bank_account"
        }
      """

      val testPayout = camelifyFieldNames(parse(examplePayoutJson)).extract[Payout]

      testPayout.id should equal("tr_10340J2eZvKYlo2Cg42HilbB")
      testPayout.amount should equal(5687)
      testPayout.arrivalDate should equal(1386374400)
      testPayout.balanceTransaction should equal("txn_19XJJ02eZvKYlo2ClwuJ1rbA")
      testPayout.created should equal(1386212146)
      testPayout.currency should equal("usd")
      testPayout.description should equal("STRIPE TRANSFER")
    }
  }
}
