package me.frmr.stripe

import net.liftweb.json._
  import JsonDSL._
import net.liftweb.util.Helpers._

case class DisputeEvidenceDetails(
  submissionCount: Int,
  dueBy: Long
)

case class DisputeEvidence(
  productDescription: Option[String],
  customerName: Option[String],
  customerEmailAddress: Option[String],
  customerPurchaseIp: Option[String],
  customerSignature: Option[String],
  billingAddress: Option[String],
  receipt: Option[String],
  shippingAddress: Option[String],
  shippingDate: Option[String],
  shippingTrackingNumber: Option[String],
  shippingDocumentation: Option[String],
  refundPolicy: Option[String],
  refundPolicyDisclosure: Option[String],
  refundRefusalExplanation: Option[String],
  cancellationPolicy: Option[String],
  cancellationPolicyDisclosure: Option[String],
  cancellationRebuttal: Option[String],
  accessActivityLog: Option[String],
  serviceDate: Option[String],
  serviceDocumentation: Option[String],
  duplicateChargeId: Option[String],
  duplicateChargeExplanation: Option[String],
  duplicateChargeDocumentation: Option[String],
  customerCommunication: Option[String],
  uncategorizedText: Option[String],
  uncategorizedFile: Option[String]
)

case class Dispute(
  charge: String,
  amount: Long,
  created: Long,
  currency: String,
  reason: String,
  status: String,
  balanceTransactions: List[BalanceTransaction],
  evidence: DisputeEvidence,
  evidenceDetails: DisputeEvidenceDetails,
  isChargeRefundable: Boolean,
  metadata: Map[String, String],
  raw: Option[JValue] = None
) extends StripeObject {
  def withRaw(raw: JValue) = this.copy(raw = Some(raw))
}
