package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Recipient(
  id: String,
  created: Long,
  livemode: Boolean,
  `type`: String,
  description: Option[String],
  email: Option[String],
  name: String,
  verified: Boolean,
  activeAccount: Option[BankAccount],
  cards: CardList,
  defaultCard: Option[String],
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}

object Recipient extends Listable[RecipientList] with Gettable[Recipient] with Deleteable {
  def baseResourceCalculator(req: Req) = req / "recipients"

  def create(
    name: String,
    `type`: String,
    taxId: Option[String] = None,
    bankAccount: Option[String] = None,
    card: Option[String] = None,
    email: Option[String] = None,
    description: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val requiredParams = Map(
      "name" -> name,
      "type" -> `type`
    )

    val optionalParams = List(
      taxId.map(("tax_id", _)),
      bankAccount.map(("bank_account", _)),
      card.map(("card", _)),
      email.map(("email", _)),
      description.map(("description", _))
    ).flatten.toMap

    val params = requiredParams ++ optionalParams ++ metadataProcessor(metadata)

    exec.executeFor[Recipient](baseResourceCalculator(exec.baseReq) << params)
  }

  def update(
    id: String,
    name: Option[String],
    taxId: Option[String] = None,
    bankAccount: Option[String] = None,
    card: Option[String] = None,
    email: Option[String] = None,
    description: Option[String] = None,
    metadata: Map[String, String] = Map.empty
  )(implicit exec: StripeExecutor) = {
    val optionalParams = List(
      name.map(("name", _)),
      taxId.map(("tax_id", _)),
      bankAccount.map(("bank_account", _)),
      card.map(("card", _)),
      email.map(("email", _)),
      description.map(("description", _))
    ).flatten.toMap

    val params = optionalParams ++ metadataProcessor(metadata)

    exec.executeFor[Recipient](baseResourceCalculator(exec.baseReq) / id << params)
  }
}
