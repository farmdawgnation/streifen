package me.frmr.stripe 

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class TransferSpec extends WordSpec with Matchers {
  implicit val formats = DefaultFormats

  "Transfer object" should {
    "retrieve correct fields from Stripe's JSON" in {
      val exampleTransferJson = """
        {
          "id": "tr_164xRv2eZvKYlo2CZxJZWm1E",
          "object": "transfer",
          "amount": 200,
          "amount_reversed": 200,
          "balance_transaction": "txn_19XJJ02eZvKYlo2ClwuJ1rbA",
          "created": 1432229235,
          "currency": "usd",
          "destination": "acct_164wxjKbnvuxQXGu",
          "destination_payment": "py_164xRvKbnvuxQXGuVFV2pZo1",
          "livemode": false,
          "metadata": {
            "order_id": "6735"
          },
          "reversals": {
            "object": "list",
            "data": [
              {
                "id": "trr_1BGmS02eZvKYlo2CklK9McmT",
                "object": "transfer_reversal",
                "amount": 100,
                "balance_transaction": "txn_1BGmS02eZvKYlo2C9f16WPBN",
                "created": 1508928572,
                "currency": "usd",
                "metadata": {
                },
                "transfer": "tr_164xRv2eZvKYlo2CZxJZWm1E"
              },
              {
                "id": "trr_1AF3y32eZvKYlo2CtkDXeobp",
                "object": "transfer_reversal",
                "amount": 100,
                "balance_transaction": "txn_1AF3y32eZvKYlo2CSgDInbEk",
                "created": 1493742915,
                "currency": "usd",
                "metadata": {
                },
                "transfer": "tr_164xRv2eZvKYlo2CZxJZWm1E"
              }
            ],
            "has_more": false,
            "total_count": 2,
            "url": "/v1/transfers/tr_164xRv2eZvKYlo2CZxJZWm1E/reversals"
          },
          "reversed": true,
          "source_transaction": "ch_164xRv2eZvKYlo2Clu1sIJWB",
          "source_type": "card",
          "transfer_group": "group_ch_164xRv2eZvKYlo2Clu1sIJWB"
        }
      """

      val testTransfer = camelifyFieldNames(parse(exampleTransferJson)).extract[Transfer]

      testTransfer.id should equal("tr_164xRv2eZvKYlo2CZxJZWm1E")
      testTransfer.amount should equal(200)
      testTransfer.amountReversed should equal(200)
      testTransfer.balanceTransaction should equal("txn_19XJJ02eZvKYlo2ClwuJ1rbA")
      testTransfer.destination should equal("acct_164wxjKbnvuxQXGu")
      testTransfer.currency should equal("usd")
    }
  }
}
