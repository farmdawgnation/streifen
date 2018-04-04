package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
  import Serialization._
  import Extraction._
import me.frmr.stripe.StripeHelpers._

import org.scalatest._

class StripeExecutorSpec extends WordSpec with Matchers {
  "StripeExecutor" should {
    "be creatable" in {
      //////
      // THIS IS WHERE I MATT FARMER HANG MY HEAD IN SHAME
      /////
      new StripeExecutor("dummy api key")
    }
  }
}
