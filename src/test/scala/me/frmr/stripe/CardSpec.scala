package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class CardSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Card object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleCardJson = """
        {
          "id": "card_14uxPs2eZvKYlo2CzgxT7pbv",
          "object": "card",
          "last4": "4242",
          "brand": "Visa",
          "funding": "credit",
          "exp_month": 1,
          "exp_year": 2050,
          "fingerprint": "Xt5EWLLDS7FJjR1c",
          "country": "US",
          "name": null,
          "address_line1": null,
          "address_line2": null,
          "address_city": null,
          "address_state": null,
          "address_zip": null,
          "address_country": null,
          "cvc_check": "pass",
          "address_line1_check": null,
          "address_zip_check": null,
          "dynamic_last4": null,
          "customer": null
        }
      """

      val testCard = camelifyFieldNames(parse(exampleCardJson)).extract[Card]

      testCard.id.get should equal("card_14uxPs2eZvKYlo2CzgxT7pbv")
      testCard.last4.get should equal("4242")
      testCard.brand.get should equal("Visa")
      testCard.funding.get should equal("credit")
      testCard.expMonth.get should equal(1)
      testCard.expYear.get should equal(2050)
      testCard.fingerprint.get should equal("Xt5EWLLDS7FJjR1c")
      testCard.country.get should equal("US")
      testCard.cvcCheck.get should equal("pass")
    }
  }
}
