package me.frmr.stripe

case class BankAccount(
  id: String,
  country: String,
  currency: String,
  defaultForCurrency: Boolean,
  last4: String,
  status: String,
  bankName: Option[String],
  fingerprint: Option[String]
)
