# Streifen

*A flexible Stripe API for Scala.*

Welcome to Streifen. This API is designed to make it both wicked easy and wicked flexible to
integrate Stripe into your webapp. It's not exactly finished yet, but I'm using it in production
since it already suits my needs and I'm pretty confident you can do the same without much fear.

The current version of the library is tested up to the **2016-03-07** version of the Stripe API.

Some things about Streifen:

* We're built on top of dispatch, so all methods of the library that talk to Stripe's servers will
  return a Scala `Future`.
* We're built on top of lift-common and lift-json. This is particularly relevant because the subtype
  of our `Futures` is a `Box`, which you can think of as `Option` on steroids. In addition to
  `Full` and `Empty` (which equate to `Some` and `None`), `Box`es also have `Failure` which can
  carry information about a failure condition. It's pretty great.
* The API is subject to change. Plan accordingly.

## Getting started

To get started with Streifen, add it to your library dependencies in your sbt buildfile!

```scala
libraryDependencies += "me.frmr.stripe" %% "streifen" % "0.0.5"
```

## Using Streifen

Using streifen is designed to be pretty simple:

1. Declare an instance of `StripeExecutor` as an `implicit`.
2. Start interacting with Stripe.

For example, if you wanted to get a list of Customers, you would do something like the following:

```scala
implicit val e = new StripeExecutor("myapikey")

Customer.list // Returns a Future[Box[CustomerList]].
```

By default, the StripeExecutor will be locked to the version of the API that the current release
is tested against. If you want to change that you could declare your executor like so:

```scala
implicit val e = new StripeExecutor("myapikey", apiVersion = "2016-03-07")
```

That would lock your app to the 2016-03-07 version of the API regardless of what the version of the
library you're using wants. *Please be aware this could cause things to break.* If for some reason
we're not pulling out data that you need from the JSON that Stripe sends us, you can get a copy of
that represented as a Lift JValue by setting the `includeRaw` parameter on the executor to true.

```scala
implicit val e = new StripeExecutor("myapikey", includeRaw = true)
```

When this is set the `raw` field, available on anything that can be returned from Stripe's servers,
will bear the raw data representation of what Stripe sent back to us. If you need immediate support
for a field that was added that we haven't added support for yet, you can access it by turning on
`includeRaw` and querying the raw parameter.

## Outstanding Items

- Finish adding Specs ensuring that we can deserialize correctly.
- Make the docs not suck.
- Improve the StripeList implementation to be a bit more generic.
- Implement pagination and querying for listing operations.
