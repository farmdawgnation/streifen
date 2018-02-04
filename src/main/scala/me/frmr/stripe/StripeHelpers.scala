package me.frmr.stripe

import net.liftweb.json._
import net.liftweb.util.Helpers._

object StripeHelpers {
  def camelifyFieldNames(input: JValue): JValue = {
    input mapField {
      case JField(fieldName, value) =>
        JField(camelifyMethod(fieldName), value)
        
      case JArray(contents) =>
        JArray(contents.mapField(camelifyFieldNames))

      case obj @ JObject(fields) =>
        JObject(fields.mapField(camelifyFieldNames).asInstanceOf[List[JField]])

      case x =>
        x
    } 
  }
}
