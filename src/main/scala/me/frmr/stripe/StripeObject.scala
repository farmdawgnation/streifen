package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
import net.liftweb.util.Helpers._

import scala.reflect.macros.Context
import scala.reflect.runtime.universe._
import scala.collection.mutable.ListBuffer

/**
 * The common ancestor of any class that represents a Stripe
 * data structure.
**/
abstract class StripeObject {
  /**
   * Return the raw JSON AST representation of the Stripe
   * data structure this class represents. Use this only if the
   * class representing your data structure doesn't already
   * provide a method for accessing the field you need.
   *
   * I'll try really hard to make sure that doesn't happen too
   * often, but no guarantees. :)
  **/
  def raw: Option[JValue]

  implicit val formats = DefaultFormats

  /**
   * Transform the underlyingData of this StripeObject then attempt to extract an
   * instance of the class T from it. This is the general implementation of extracting
   * values from the JSON API response from Stripe.
   *
   * You should only use this if you're accessing a piece of data from the raw response
   * that we don't support. If you do find need to use this, we'd love it if you opened
   * a pull request!
   *
   * @param transformer The function that transforms the original data into the structure containing the data we want.
   * @return A Full[T] if the extraction was successful, a Failure otherwise.
  **/
  def valueFor[T](transformer: (JValue)=>JValue)(implicit mf: Manifest[T]) =
    tryo(transformer(raw.getOrElse(JNothing)).extract[T](formats, mf)).filterNot(_ == null)
}

/**
 * The base trait for the implementation of meta objects used for communicating
 * with Stripe's servers.
**/
trait StripeMeta[T <: StripeObject] {

}

trait Gettable
trait Creatable
trait Listable
trait Updateable
trait Deleteable
