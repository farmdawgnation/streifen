package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class AccountSpec extends WordSpec with ShouldMatchers {
  implicit val formats = DefaultFormats

  "Account object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val json = """
        {
          "id": "acct_1032D82eZvKYlo2C",
          "email": "site@stripe.com",
          "statement_descriptor": null,
          "display_name": "Stripe.com",
          "timezone": "US/Pacific",
          "details_submitted": false,
          "charges_enabled": false,
          "transfers_enabled": false,
          "currencies_supported": [
            "usd",
            "aed",
            "afn"
          ],
          "default_currency": "usd",
          "country": "US",
          "object": "account",
          "business_name": "Stripe.com"
        }
      """

      val testAccount = camelifyFieldNames(parse(json)).extract[Account]

      testAccount.id should equal("acct_1032D82eZvKYlo2C")
      testAccount.email should equal("site@stripe.com")
      testAccount.statementDescriptor should equal(None)
      testAccount.displayName should equal("Stripe.com")
      testAccount.timezone should equal("US/Pacific")
      testAccount.detailsSubmitted should equal(false)
      testAccount.chargesEnabled should equal(false)
      testAccount.transfersEnabled should equal(false)
      testAccount.currenciesSupported(0) should equal("usd")
      testAccount.defaultCurrency should equal("usd")
      testAccount.country should equal("US")
      testAccount.businessName should equal("Stripe.com")
    }
  }
}
