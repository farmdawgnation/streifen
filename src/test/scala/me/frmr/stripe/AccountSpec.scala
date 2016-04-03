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
          "object": "account",
          "business_logo": null,
          "business_name": "Stripe.com",
          "business_url": null,
          "charges_enabled": false,
          "country": "US",
          "debit_negative_balances": true,
          "decline_charge_on": {
            "avs_failure": false,
            "cvc_failure": false
          },
          "default_currency": "usd",
          "details_submitted": false,
          "display_name": "Stripe.com",
          "email": "site@stripe.com",
          "external_accounts": {
            "object": "list",
            "data": [

            ],
            "has_more": false,
            "total_count": 0,
            "url": "/v1/accounts/acct_1032D82eZvKYlo2C/external_accounts"
          },
          "legal_entity": {
            "additional_owners": null,
            "address": {
              "city": null,
              "country": "US",
              "line1": null,
              "line2": null,
              "postal_code": null,
              "state": null
            },
            "address_kana": {
              "city": null,
              "country": "US",
              "line1": null,
              "line2": null,
              "postal_code": null,
              "state": null,
              "town": null
            },
            "address_kanji": {
              "city": null,
              "country": "US",
              "line1": null,
              "line2": null,
              "postal_code": null,
              "state": null,
              "town": null
            },
            "business_name": null,
            "business_name_kana": null,
            "business_name_kanji": null,
            "business_tax_id_provided": false,
            "dob": {
              "day": null,
              "month": null,
              "year": null
            },
            "first_name": null,
            "first_name_kana": null,
            "first_name_kanji": null,
            "gender": null,
            "last_name": null,
            "last_name_kana": null,
            "last_name_kanji": null,
            "maiden_name": null,
            "personal_address": {
              "city": null,
              "country": null,
              "line1": null,
              "line2": null,
              "postal_code": null,
              "state": null
            },
            "personal_address_kana": {
              "city": null,
              "country": null,
              "line1": null,
              "line2": null,
              "postal_code": null,
              "state": null,
              "town": null
            },
            "personal_address_kanji": {
              "city": null,
              "country": null,
              "line1": null,
              "line2": null,
              "postal_code": null,
              "state": null,
              "town": null
            },
            "personal_id_number_provided": false,
            "phone_number": null,
            "ssn_last_4_provided": false,
            "type": null,
            "verification": {
              "details": null,
              "details_code": "failed_other",
              "document": null,
              "status": "unverified"
            }
          },
          "managed": false,
          "product_description": null,
          "statement_descriptor": null,
          "support_email": null,
          "support_phone": null,
          "timezone": "US/Pacific",
          "tos_acceptance": {
            "date": null,
            "ip": null,
            "user_agent": null
          },
          "transfer_schedule": {
            "delay_days": 7,
            "interval": "daily"
          },
          "transfers_enabled": false,
          "verification": {
            "disabled_reason": "fields_needed",
            "due_by": null,
            "fields_needed": [
            "business_url",
            "external_account",
            "product_description",
            "support_phone",
            "tos_acceptance.date",
            "tos_acceptance.ip"
            ]
          }
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
      testAccount.defaultCurrency should equal("usd")
      testAccount.country should equal("US")
      testAccount.businessName should equal("Stripe.com")
    }
  }
}
