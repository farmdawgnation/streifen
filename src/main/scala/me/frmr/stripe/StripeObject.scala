package me.frmr.stripe

import net.liftweb.json._
import net.liftweb.util.Helpers._

import scala.reflect.macros.Context
import scala.reflect.runtime.universe._
import scala.collection.mutable.ListBuffer

/**
 * The common ancestor of any class that represents a Stripe
 * data structure.
**/
abstract class StripeObject(underlyingData: JValue) {
  /**
   * Return the raw JSON AST representation of the Stripe
   * data structure this class represents. Use this only if the
   * class representing your data structure doesn't already
   * provide a method for accessing the field you need.
   *
   * I'll try really hard to make sure that doesn't happen too
   * often, but no guarantees. :)
  **/
  def raw = underlyingData

  implicit val formats = DefaultFormats

  /**
   * Transform the underlyingData of this StripeObject then attempt to extract an
   * instance of the class T from it. This is the general implementation of extracting
   * values from the JSON API response from Stripe.
   *
   * You should only use this in an implementing class of StripeObject if the value you
   * want to extract isn't one of the primitive types we provide helpers for below.
   *
   * @param transformer The function that transforms the original data into the structure containing the data we want.
   * @return A Full[T] if the extraction was successful, a Failure otherwise.
  **/
  protected def valueFor[T](transformer: (JValue)=>JValue)(implicit mf: Manifest[T]) = {
    val result = tryo(transformer(raw).extract[T](formats, mf)).filterNot(_ == null)

    result match {
      case Failure(_, Full(exception), _) => exception.printStackTrace
      case _ =>
    }

    result
  }

  protected def stringValueFor(transformer: (JValue)=>JValue) =
    valueFor[String](transformer)

  protected def booleanValueFor(transformer: (JValue)=>JValue) =
    valueFor[Boolean](transformer)

  protected def intValueFor(transformer: (JValue)=>JValue) =
    valueFor[Int](transformer)

  protected def longValueFor(transformer: (JValue)=>JValue) =
    valueFor[Long](transformer)

  protected def mapValueFor(transformer: (JValue)=>JValue) =
    valueFor[Map[String, String]](transformer)
}
