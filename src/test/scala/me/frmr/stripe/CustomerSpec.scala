package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class CustomerSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Customer object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleCustomerJson = """
        {
          "object": "customer",
          "created": 1414861702,
          "id": "cus_54DocayG4yRrNK",
          "livemode": false,
          "description": "nkajok@coolfiresolutions.com",
          "email": null,
          "delinquent": false,
          "metadata": {
          },
          "subscriptions": {
            "object": "list",
            "total_count": 0,
            "has_more": false,
            "url": "/v1/customers/cus_54DocayG4yRrNK/subscriptions",
            "data": [

            ]
          },
          "discount": null,
          "account_balance": 0,
          "currency": "usd",
          "cards": {
            "object": "list",
            "total_count": 1,
            "has_more": false,
            "url": "/v1/customers/cus_54DocayG4yRrNK/cards",
            "data": [
              {
                "id": "card_14u5MP2eZvKYlo2CIukHVUb4",
                "object": "card",
                "last4": "4242",
                "brand": "Visa",
                "funding": "credit",
                "exp_month": 12,
                "exp_year": 2014,
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
                "customer": "cus_54DocayG4yRrNK"
              }
            ]
          },
          "default_card": "card_14u5MP2eZvKYlo2CIukHVUb4"
        }
      """

      val testCustomer = camelifyFieldNames(parse(exampleCustomerJson)).extract[Customer]

      testCustomer.id should equal("cus_54DocayG4yRrNK")
      testCustomer.livemode should equal(false)
      testCustomer.created should equal(1414861702)
      testCustomer.accountBalance should equal(0)
      testCustomer.currency should equal("usd")
      testCustomer.defaultCard.get should equal("card_14u5MP2eZvKYlo2CIukHVUb4")
      testCustomer.delinquent should equal(false)
      testCustomer.description.get should equal("nkajok@coolfiresolutions.com")
      testCustomer.email should equal(None)
      testCustomer.metadata should equal(Map.empty)
      testCustomer.discount should equal(None)
    }
  }
}
