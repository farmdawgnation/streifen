package me.frmr.stripe

import net.liftweb.json._
  import Serialization._
  import JsonDSL._
  import Extraction._

import java.util.Arrays
import scala.collection.JavaConversions._

trait StripeList[T] extends StripeObject {
  val data: List[T]
  val hasMore: Boolean
  val totalCount: Option[Int]
  val url: String
  val raw: Option[JValue]
}

case class CardList(
  data: List[Card],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Card] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class SubscriptionList(
  data: List[Subscription],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Subscription] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class RefundList(
  data: List[Refund],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Refund] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class ApplicationFeeList(
  data: List[ApplicationFee],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[ApplicationFee] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class ApplicationFeeRefundList(
  data: List[ApplicationFeeRefund],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[ApplicationFeeRefund] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class CustomerList(
  data: List[Customer],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Customer] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class RecipientList(
  data: List[Recipient],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Recipient] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class TransferList(
  data: List[Transfer],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Transfer] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class ChargeList(
  data: List[Charge],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Charge] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class PlanList(
  data: List[Plan],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Plan] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class CouponList(
  data: List[Coupon],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Coupon] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class InvoiceLineItemList(
  data: List[InvoiceLineItem],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[InvoiceLineItem]  {
  def this(data: List[InvoiceLineItem], totalCount: Option[Int], url: String, raw: Option[JValue]) =
    this(data, false, totalCount, url, raw)
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class InvoiceList(
  data: List[Invoice],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Invoice] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class InvoiceItemList(
  data: List[InvoiceItem],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[InvoiceItem] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

case class EventList(
  data: List[Event],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = "",
  raw: Option[JValue] = None
) extends StripeList[Event] {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}
