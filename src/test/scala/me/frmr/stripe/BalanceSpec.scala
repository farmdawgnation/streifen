package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class BalanceSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Balance object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleBalanceJson = """
        {
          "object": "balance",
          "available": [
          {
            "currency": "usd",
            "amount": -2352,
            "source_types": {
              "card": 8032622669,
              "bank_account": 8509784,
              "bitcoin_receiver": 1449199
            }
          }
          ],
          "livemode": false,
          "pending": [
          {
            "currency": "usd",
            "amount": 4966640,
            "source_types": {
              "card": 746697673,
              "bank_account": 0,
              "bitcoin_receiver": 0
            }
          }
          ]
        }
      """

      val testBalance = camelifyFieldNames(parse(exampleBalanceJson)).extract[Balance]

      testBalance.pending(0).amount should equal(4966640)
      testBalance.pending(0).currency should equal("usd")
      testBalance.available(0).amount should equal(-2352)
      testBalance.available(0).currency should equal("usd")
      testBalance.livemode should equal(false)
    }
  }
}
