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

case class CustomerList(
  data: List[Customer],
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
) extends StripeList[Customer]
