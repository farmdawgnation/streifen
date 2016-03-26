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
          "id": "card_17tOG52eZvKYlo2CfEJFhcJ2",
          "object": "card",
          "address_city": null,
          "address_country": null,
          "address_line1": null,
          "address_line1_check": null,
          "address_line2": null,
          "address_state": null,
          "address_zip": null,
          "address_zip_check": null,
          "brand": "Visa",
          "country": "US",
          "customer": null,
          "cvc_check": "pass",
          "dynamic_last4": null,
          "exp_month": 11,
          "exp_year": 2022,
          "fingerprint": "jfYpjs0oYxBYKc4C",
          "funding": "credit",
          "last4": "4242",
          "metadata": {
          },
          "name": "tickle.me.edmo@gmail.com",
          "tokenization_method": null
        }
      """

      val testCard = camelifyFieldNames(parse(exampleCardJson)).extract[Card]

      testCard.id should equal("card_17tOG52eZvKYlo2CfEJFhcJ2")
      testCard.last4 should equal("4242")
      testCard.brand should equal("Visa")
      testCard.funding should equal("credit")
      testCard.expMonth should equal(11)
      testCard.expYear should equal(2022)
      testCard.fingerprint should equal("jfYpjs0oYxBYKc4C")
      testCard.country.get should equal("US")
      testCard.cvcCheck.get should equal("pass")
    }
  }
}
