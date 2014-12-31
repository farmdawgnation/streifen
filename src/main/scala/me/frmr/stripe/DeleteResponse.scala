package me.frmr.stripe

import net.liftweb.json._

case class DeleteResponse(
  deleted: Boolean,
  id: String,
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}
