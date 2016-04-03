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
          "id": "cus_89c8SrPpHZP4ex",
          "object": "customer",
          "account_balance": 0,
          "created": 1459004472,
          "currency": "usd",
          "default_source": "card_17tIuV2eZvKYlo2Cf2npn5PC",
          "delinquent": false,
          "description": "Customer for test@example.com",
          "discount": null,
          "email": "y@ymail.com",
          "livemode": false,
          "metadata": {
          },
          "shipping": null,
          "sources": {
            "object": "list",
            "data": [
            {
              "id": "card_17tIuV2eZvKYlo2Cf2npn5PC",
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
              "customer": "cus_89c8SrPpHZP4ex",
              "cvc_check": "pass",
              "dynamic_last4": null,
              "exp_month": 1,
              "exp_year": 2019,
              "fingerprint": "Xt5EWLLDS7FJjR1c",
              "funding": "credit",
              "last4": "4242",
              "metadata": {
              },
              "name": "y@ymail.com",
              "tokenization_method": null
            }
            ],
            "has_more": false,
            "total_count": 1,
            "url": "/v1/customers/cus_89c8SrPpHZP4ex/sources"
          },
          "subscriptions": {
            "object": "list",
            "data": [

            ],
            "has_more": false,
            "total_count": 0,
            "url": "/v1/customers/cus_89c8SrPpHZP4ex/subscriptions"
          }
        }
      """

      val testCustomer = camelifyFieldNames(parse(exampleCustomerJson)).extract[Customer]

      testCustomer.id should equal("cus_89c8SrPpHZP4ex")
      testCustomer.livemode should equal(false)
      testCustomer.created should equal(1459004472)
      testCustomer.accountBalance should equal(0)
      testCustomer.currency should equal("usd")
      testCustomer.defaultSource.get should equal("card_17tIuV2eZvKYlo2Cf2npn5PC")
      testCustomer.delinquent should equal(false)
      testCustomer.description.get should equal("Customer for test@example.com")
      testCustomer.email should equal(Some("y@ymail.com"))
      testCustomer.metadata should equal(Map.empty)
      testCustomer.discount should equal(None)
    }
  }
}
