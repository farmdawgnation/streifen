package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
import net.liftweb.util._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._
import org.asynchttpclient.Response

/**
 * The base class for all singletons that will facilitate communication
 * with Stripe on behalf of the top-level model objects.
**/
trait StripeMeta {
  implicit val formats = DefaultFormats

  /**
   * This function should return the base resource URL for whatever kind
   * of resource this is.
  **/
  def baseResourceCalculator(request: Req): Req

  def metadataProcessor(metadata: Map[String, String]) = {
    metadata.map({
      case (key, value) =>
        (s"metadata[$key]", value)
    })
  }
}

trait Gettable[T <: StripeObject] extends StripeMeta {
  def get(id: String)(implicit exec: StripeExecutor, mf: Manifest[T]): Future[Box[T]] = {
    val getReq = baseResourceCalculator(exec.baseReq) / id
    exec.executeFor[T](getReq)
  }
}

abstract class Listable[Z <: StripeList[_]](implicit mf: Manifest[Z]) extends StripeMeta {
  def list(implicit exec: StripeExecutor): Future[Box[Z]] = {
    exec.executeFor[Z](baseResourceCalculator(exec.baseReq))
  }
}

trait Deleteable extends StripeMeta {
  def delete(id: String)(implicit exec: StripeExecutor): Future[Box[DeleteResponse]] = {
    val deleteReq = (baseResourceCalculator(exec.baseReq) / id).DELETE
    exec.executeFor[DeleteResponse](deleteReq)
  }
}

/**
 * The base class for all singletons that will facilitate communication
 * with Stripe on behalf of the child-level model objects.
**/
trait ChildStripeMeta {
  implicit val formats = DefaultFormats

  /**
   * This function should return the base resource URL for whatever kind
   * of resource this is.
  **/
  def baseResourceCalculator(request: Req, parentId: String): Req

  def metadataProcessor(metadata: Map[String, String]) = {
    metadata.map({
      case (key, value) =>
        (s"metadata[$key]", value)
    })
  }
}

trait ChildGettable[T <: StripeObject] extends ChildStripeMeta {
  def get(parentId: String, id: String)(implicit exec: StripeExecutor, mf: Manifest[T]): Future[Box[T]] = {
    val getReq = baseResourceCalculator(exec.baseReq, parentId) / id
    exec.executeFor[T](getReq)
  }
}

abstract class ChildListable[Z <: StripeList[_]](implicit mf: Manifest[Z]) extends ChildStripeMeta {
  def list(parentId: String)(implicit exec: StripeExecutor): Future[Box[Z]] = {
    exec.executeFor[Z](baseResourceCalculator(exec.baseReq, parentId: String))
  }
}

trait ChildDeleteable extends ChildStripeMeta {
  def delete(parentId: String, id: String)(implicit exec: StripeExecutor): Future[Box[DeleteResponse]] = {
    val deleteReq = (baseResourceCalculator(exec.baseReq, parentId) / id).DELETE
    exec.executeFor[DeleteResponse](deleteReq)
  }
}
