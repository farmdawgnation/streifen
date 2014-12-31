package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import JsonDSL._
  import Extraction._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._

case class Event(
  id: String,
  livemode: Boolean,
  created: Long,
  data: JObject,
  pendingWebhooks: Int,
  `type`: String,
  apiVersion: String,
  request: String,
  customerEmail: Option[String],
  raw: Option[JValue] = None
) extends StripeObject

object Event extends Listable[EventList] with Gettable[Event] {
  def baseResourceCalculator(req: Req) = req / "events"
}
