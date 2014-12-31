package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class BalanceTransactionSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "BalanceTransaction object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleJson = """
        {
          "id": "txn_15DRvX2eZvKYlo2CgxMQ25SQ",
          "object": "balance_transaction",
          "amount": 500,
          "currency": "usd",
          "net": 455,
          "type": "charge",
          "created": 1419476679,
          "available_on": 1420070400,
          "status": "pending",
          "fee": 45,
          "fee_details": [
            {
              "amount": 45,
              "currency": "usd",
              "type": "stripe_fee",
              "description": "Stripe processing fees",
              "application": null
            }
          ],
          "source": "ch_15DRvX2eZvKYlo2CzgFB69s3",
          "description": null
        }
      """

      val testBalanceTransaction = camelifyFieldNames(parse(exampleJson)).extract[BalanceTransaction]

      testBalanceTransaction.id should equal("txn_15DRvX2eZvKYlo2CgxMQ25SQ")
      testBalanceTransaction.amount should equal(500)
      testBalanceTransaction.currency should equal("usd")
      testBalanceTransaction.net should equal(455)
      testBalanceTransaction.`type` should equal("charge")
      testBalanceTransaction.created should equal(1419476679)
      testBalanceTransaction.availableOn should equal(1420070400)
      testBalanceTransaction.status should equal("pending")
      testBalanceTransaction.fee should equal(45)
      testBalanceTransaction.source should equal("ch_15DRvX2eZvKYlo2CzgFB69s3")
      testBalanceTransaction.description should equal(None)
      testBalanceTransaction.feeDetails(0).amount should equal(45)
      testBalanceTransaction.feeDetails(0).currency should equal("usd")
      testBalanceTransaction.feeDetails(0).`type` should equal("stripe_fee")
      testBalanceTransaction.feeDetails(0).description should equal(Some("Stripe processing fees"))
      testBalanceTransaction.feeDetails(0).application should equal(None)
    }
  }
}
