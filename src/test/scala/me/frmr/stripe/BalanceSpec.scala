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
          "pending": [
            {
              "amount": 4966640,
              "currency": "usd"
            }
          ],
          "available": [
            {
              "amount": -2352,
              "currency": "usd"
            }
          ],
          "livemode": false,
          "object": "balance"
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
