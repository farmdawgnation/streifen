package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Product(
  id: String,
  active: Option[Boolean],
  name: String,
  created: Long,
  updated: Long,
  url: Option[String],
  shippable: Option[Boolean],
  statementDescriptor: Option[String],
  `type`: String,
  caption: Option[String],
  description: Option[String],
  metadata: Map[String, String],
  raw: Option[JValue]
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object Product extends Listable[ProductList] with Gettable[Product] with Deleteable {
  def baseResourceCalculator(req: Req) =
    req / "products"

  def create(
    name: String,
    `type`: String,
    id: Option[String],
    active: Option[Boolean] = None,
    shippable: Option[Boolean] = None,
    caption: Option[String] = None,
    description: Option[String] = None,
    statementDescriptor: Option[String] = None,
    url: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "name" -> name,
      "type" -> `type`
    )

    val optionalParams = List(
      id.map(("id", _)),
      active.map(active => ("active", active.toString)),
      shippable.map(shippable => ("shippable", shippable.toString)),
      caption.map(("caption", _)),
      description.map(("description", _)),
      url.map(("url", _)),
      statementDescriptor.map(("statement_descriptor", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq)

    exec.executeFor[Product](uri << params)
  }

  def update(
    id: String,
    name: Option[String] = None,
    active: Option[Boolean] = None,
    shippable: Option[Boolean] = None,
    caption: Option[String] = None,
    description: Option[String] = None,
    url: Option[String] = None,
    metadata: Map[String, String] = Map.empty,
    statementDescriptor: Option[String] = None
  )(implicit exec: StripeExecutor) = {
    val params = List(
      active.map(active => ("active", active.toString)),
      shippable.map(shippable => ("shippable", shippable.toString)),
      caption.map(("caption", _)),
      description.map(("description", _)),
      url.map(("url", _)),
      statementDescriptor.map(("statement_descriptor", _))
    ).flatten.toMap ++ metadataProcessor(metadata)
    val uri = baseResourceCalculator(exec.baseReq) / id

    exec.executeFor[Product](uri << params)
  }
}

