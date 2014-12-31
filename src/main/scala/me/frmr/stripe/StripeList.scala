package me.frmr.stripe

import net.liftweb.json._
  import Serialization._
  import JsonDSL._
  import Extraction._

import java.util.Arrays
import scala.collection.JavaConversions._

trait StripeList[T] {
  val data: List[T]
  val hasMore: Boolean
  val totalCount: Option[Int]
  val url: String
}

case class CardList(
  data: List[Card],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Card]

case class SubscriptionList(
  data: List[Subscription],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Subscription]

case class RefundList(
  data: List[Refund],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Refund]

case class ApplicationFeeList(
  data: List[ApplicationFee],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[ApplicationFee]

case class ApplicationFeeRefundList(
  data: List[ApplicationFeeRefund],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[ApplicationFeeRefund]

case class CustomerList(
  data: List[Customer],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Customer]

case class RecipientList(
  data: List[Recipient],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Recipient]

case class TransferList(
  data: List[Transfer],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Transfer]

case class ChargeList(
  data: List[Charge],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Charge]

case class PlanList(
  data: List[Plan],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Plan]

case class CouponList(
  data: List[Coupon],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Coupon]

case class InvoiceLineItemList(
  data: List[InvoiceLineItem],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[InvoiceLineItem]

case class InvoiceList(
  data: List[Invoice],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Invoice]

case class InvoiceItemList(
  data: List[InvoiceItem],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[InvoiceItem]

case class EventList(
  data: List[Event],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Event]
