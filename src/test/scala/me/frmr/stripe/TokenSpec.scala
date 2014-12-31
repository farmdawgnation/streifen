package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class TokenSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Token object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleTokenJson = """
        {
          "id": "tok_15Fd2I2eZvKYlo2CnJvoo1FX",
          "livemode": false,
          "created": 1419996038,
          "used": false,
          "object": "token",
          "type": "card",
          "card": {
            "id": "card_15Fd2I2eZvKYlo2CKcSU943C",
            "object": "card",
            "last4": "4242",
            "brand": "Visa",
            "funding": "credit",
            "exp_month": 8,
            "exp_year": 2015,
            "fingerprint": "Xt5EWLLDS7FJjR1c",
            "country": "US",
            "name": null,
            "address_line1": null,
            "address_line2": null,
            "address_city": null,
            "address_state": null,
            "address_zip": null,
            "address_country": null,
            "cvc_check": null,
            "address_line1_check": null,
            "address_zip_check": null,
            "dynamic_last4": null
          }
        }
      """

      val testToken = camelifyFieldNames(parse(exampleTokenJson)).extract[Token]

      testToken.id should equal("tok_15Fd2I2eZvKYlo2CnJvoo1FX")
      testToken.livemode should equal(false)
      testToken.created should equal(1419996038)
      testToken.used should equal(false)
      testToken.`type` should equal("card")
    }
  }
}
