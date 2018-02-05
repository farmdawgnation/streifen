package me.frmr.stripe

import net.liftweb.json._
import net.liftweb.util.Helpers._

object StripeHelpers {
  def camelifyFieldNames(input: JValue): JValue = {
    input transformField {
      case JField(fieldName, value) =>
        JField(camelifyMethod(fieldName), value)
 
      case x =>
        x
    } 
  }
}
