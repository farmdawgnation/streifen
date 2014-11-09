package me.frmr.stripe

import net.liftweb.common._
import net.liftweb.json._
import net.liftweb.util._
import net.liftweb.util.Helpers._

import dispatch._, Defaults._
import com.ning.http.client.Response

/**
 * Case class describing a raw response from Stripe, including
 * HTTP status code and JValue representation of the payload.
**/
case class StripeResponse(code: Int, json: JValue)

/**
 * Response transformer for use with dispatch to turn a Response
 * into an instance of StripeResponse.
**/
object AsStripeResponse extends (Response=>StripeResponse) {
  def apply(res: Response) = {
    StripeResponse(res.getStatusCode(), StripeHelpers.camelifyFieldNames(as.lift.Json(res)))
  }
}

/**
 * An executor that will talk to Stripe for you. You'll need to
 * define an executor in your code that knows about your API key
 * in order to talk to Stripe.
 *
 * StripeExecutors are designed to be used as implicits, so that they
 * can be defined once in an application and automatically dropped in
 * wherever they're needed. For example, for your application you might
 * use the package prefix com.widgets. To put an executor in scope for all
 * of your code you'd define a package object containing the executor.
 *
 * {{{
 * package com
 * package object widgets {
 *   implicit val stripeExecutor = new StripeExecutor(myApiKey)
 * }
 * }}}
**/
class StripeExecutor(apiKey: String) {
  val httpExecutor = new Http()
  val baseReq = url("https://api.stripe.com/v1").secure.as(apiKey, "")
  implicit val formats = DefaultFormats

  def execute(request: Req): Future[Box[StripeResponse]] = {
    httpExecutor(request > AsStripeResponse).either.map {
      case Left(throwable) =>
        Failure("Error occured while talking to stripe.", Full(throwable), Empty)

      case Right(stripeResponse) =>
        Full(stripeResponse)
    }
  }

  protected def handler[T](implicit mf: Manifest[T]): (StripeResponse)=>Box[T] = {
    case StripeResponse(200, json) =>
      tryo(json.extract[T])

    case StripeResponse(code, json) =>
      Failure(s"Unexpected $code") ~> json
  }

  def executeFor[T](request: Req)(implicit mf: Manifest[T]): Future[Box[T]] = {
    execute(request).map { futureBox =>
      for {
        stripeResponse <- futureBox
        concreteObject <- handler(mf)(stripeResponse)
      } yield {
        concreteObject
      }
    }
  }
}
