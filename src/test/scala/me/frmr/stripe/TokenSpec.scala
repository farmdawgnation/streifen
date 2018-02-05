package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class TokenSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "Token object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleTokenJson = """
        {
          "id": "tok_17szAe2eZvKYlo2Ch8eMPKHH",
          "object": "token",
          "card": {
            "id": "card_17szAe2eZvKYlo2Cg5FDnNgj",
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
            "cvc_check": null,
            "dynamic_last4": null,
            "exp_month": 8,
            "exp_year": 2017,
            "funding": "credit",
            "last4": "4242",
            "metadata": {
            },
            "name": null,
            "tokenization_method": null
          },
          "client_ip": null,
          "created": 1458928588,
          "livemode": false,
          "type": "card",
          "used": false
        }
      """

      val testToken = camelifyFieldNames(parse(exampleTokenJson)).extract[Token]

      testToken.id should equal("tok_17szAe2eZvKYlo2Ch8eMPKHH")
      testToken.livemode should equal(false)
      testToken.created should equal(1458928588)
      testToken.used should equal(false)
      testToken.`type` should equal("card")
    }
  }
}
