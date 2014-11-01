package me.frmr.stripe

case class StripeList[T](
  `object`: String = "list",
  data: List[T] = Nil,
  hasMore: Boolean = false,
  totalCount: Option[Int] = None,
  url: String = ""
)

object StripeList {
  implicit def toStripeList[T](data: List[T]) = StripeList[T](data = data)
}
