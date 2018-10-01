package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class BalanceTransactionSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "BalanceTransaction object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleJson = """
        {
          "id": "txn_17bBwe2eZvKYlo2Cuwcyi9or",
          "object": "balance_transaction",
          "amount": 400,
          "available_on": 1455235200,
          "created": 1454687788,
          "currency": "usd",
          "description": "Charge for test@example.com",
          "fee": 42,
          "fee_details": [
          {
            "amount": 42,
            "application": null,
            "currency": "usd",
            "description": "Stripe processing fees",
            "type": "stripe_fee"
          }
          ],
          "net": 358,
          "source": "ch_17bBwe2eZvKYlo2Crk3VGEG8",
          "status": "pending",
          "type": "charge"
        }
      """

      val testBalanceTransaction = camelifyFieldNames(parse(exampleJson)).extract[BalanceTransaction]

      testBalanceTransaction.id should equal("txn_17bBwe2eZvKYlo2Cuwcyi9or")
      testBalanceTransaction.amount should equal(400)
      testBalanceTransaction.currency should equal("usd")
      testBalanceTransaction.net should equal(358)
      testBalanceTransaction.`type` should equal("charge")
      testBalanceTransaction.created should equal(1454687788)
      testBalanceTransaction.availableOn should equal(1455235200)
      testBalanceTransaction.status should equal("pending")
      testBalanceTransaction.fee should equal(42)
      testBalanceTransaction.source should equal("ch_17bBwe2eZvKYlo2Crk3VGEG8")
      testBalanceTransaction.description should equal(Some("Charge for test@example.com"))
      testBalanceTransaction.feeDetails(0).amount should equal(42)
      testBalanceTransaction.feeDetails(0).currency should equal("usd")
      testBalanceTransaction.feeDetails(0).`type` should equal("stripe_fee")
      testBalanceTransaction.feeDetails(0).description should equal(Some("Stripe processing fees"))
      testBalanceTransaction.feeDetails(0).application should equal(None)
    }
  }
}
