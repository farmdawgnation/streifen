# Streifen

*A flexible Stripe API for Scala.*

Welcome to Streifen. This API is designed to make it both wicked easy and wicked flexible to
integrate Stripe into your webapp. It's not exactly finished yet, but I'm using it in production
since it already suits my needs and I'm pretty confident you can do the same without much fear.

Some things about Streifen:

* We're built on top of dispatch, so all methods of the library that talk to Stripe's servers will
  return a `Future`.
* We're built on top of lift-common and lift-json. This is particularly relevant because the subtype
  of our `Futures` is a `Box`, which you can think of as `Option` on steroids. In addition to
  `Full` and `Empty` (which equate to `Some` and `None`), `Box`es also have `Failure` which can
  carry information about a failure condition. It's pretty great.
* The API is subject to change. Plan accordingly.
* It's not on Maven yet because Lift 2.6 hasn't gone final and the dispatch guys won't be able to
  spin out a 2.11 build of dispatch-liftjson on Maven until that happens. Sorry.

## Using Streifen

Using streifen is designed to be pretty simple:

1. Declare an instance of `StripeExecutor` as an `implicit`.
2. Start interacting with Stripe.

```scala
implicit val e = new StripeExecutor("myapikey")

Customer.list // Returns a Future[Box[CustomerList]].
```

## Current status

Currently, we only have support for interacting with:

* Customers.
* Subscriptions.

Support for the entire API is on the plans though.

## Setting up Streifen

Since we depend on an unreleased version of dispatch, you'll have to do some legwork to get
streifen available on your dev machine or servers. Specifically, you'll need to clone my fork
of dispatch and publish it locally before building and publishing streifen locally.

```
$ git clone git@github.com:farmdawgnation/reboot
$ cd reboot
$ git checkout json_on_2.11
$ sbt publish-local
$ cd ..
$ git clone git@github.com:farmdawgnation/streifen
$ cd streifen
$ sbt publish-local
```
