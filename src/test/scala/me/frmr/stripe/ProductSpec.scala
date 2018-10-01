package me.frmr.stripe 

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class ProductSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "Product object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleProductJson = """
        {
          "id": "prod_BosWT9EsdzgjPn",
          "object": "product",
          "active": false,
          "attributes": [
            "size",
            "gender"
          ],
          "caption": "test",
          "created": 1511420673,
          "deactivate_on": [],
          "description": "Comfortable cotton t-shirt",
          "images": [],
          "livemode": false,
          "metadata": {},
          "name": "T-shirt",
          "package_dimensions": {
            "height": 1.25,
            "length": 2.44,
            "weight": 3.0,
            "width": 4.0
          },
          "shippable": false,
          "skus": {
            "object": "list",
            "data": [
              {
                "id": "sku_C3rCQ7Eq4wRfLw",
                "object": "sku",
                "active": true,
                "attributes": {
                  "size": "Medium",
                  "gender": "Unisex"
                },
                "created": 1514875358,
                "currency": "usd",
                "image": null,
                "inventory": {
                  "quantity": 500,
                  "type": "finite",
                  "value": null
                },
                "livemode": false,
                "metadata": {
                },
                "package_dimensions": null,
                "price": 1500,
                "product": "prod_BosWT9EsdzgjPn",
                "updated": 1514875358
              }
            ],
            "has_more": false,
            "total_count": 1,
            "url": "/v1/skus?product=prod_BosWT9EsdzgjPn\u0026active=true"
          },
          "type": "good",
          "updated": 1511422435,
          "url": "https://api.stripe.com/"
        }
      """

      val testProduct = camelifyFieldNames(parse(exampleProductJson)).extract[Product]

      testProduct.id should equal("prod_BosWT9EsdzgjPn")
      testProduct.active should equal(Some(false))
      testProduct.caption should equal(Some("test"))
      testProduct.description should equal(Some("Comfortable cotton t-shirt"))
      testProduct.name should equal("T-shirt")
      testProduct.shippable should equal(Some(false))
      testProduct.`type` should equal("good")
      testProduct.updated should equal(1511422435)
      testProduct.url should equal(Some("https://api.stripe.com/"))
    }
  }
}

