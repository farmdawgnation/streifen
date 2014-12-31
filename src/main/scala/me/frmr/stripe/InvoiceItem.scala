package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class InvoiceItem(
  id: String,
  livemode: Boolean,
  amount: Long,
  currency: String,
  customer: String,
  date: Long,
  proration: Boolean,
  description: String,
  invoice: String,
  plan: Option[Plan],
  quantity: Int,
  subscription: String,
  statementDescriptor: Option[String],
  metadata: Map[String, String],
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object InvoiceItem extends Listable[InvoiceItemList] with Gettable[InvoiceItem] with Deleteable {
  def baseResourceCalculator(req: Req) =
    req / "invoiceitems"

  def create(
    customer: String,
    amount: Long,
    currency: String,
    invoice: Option[String] = None,
    subscription: Option[String] = None,
    description: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "customer" -> customer,
      "amount" -> amount.toString,
      "currency" -> currency
    )
    val optionalParams = List(
      invoice.map(("invoice", _)),
      subscription.map(("subscription", _)),
      description.map(("description", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq)

    exec.executeFor[InvoiceItem](uri << params)
  }

  def update(
    id: String,
    amount: Option[Long] = None,
    description: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val params = List(
      amount.map(a => ("amount", a.toString)),
      description.map(("description", _))
    ).flatten.toMap ++ metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq) / id

    exec.executeFor[InvoiceItem](uri << params)
  }
}
