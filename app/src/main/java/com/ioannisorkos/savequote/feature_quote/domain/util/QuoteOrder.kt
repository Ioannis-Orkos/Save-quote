package com.ioannisorkos.savequote.feature_quote.domain.util


sealed class QuoteOrder(val orderType: OrderType) {
    class By(orderType: OrderType): QuoteOrder(orderType)
   // class Source(orderType: OrderType): QuoteOrder(orderType)
    class Date(orderType: OrderType): QuoteOrder(orderType)
    class Color(orderType: OrderType): QuoteOrder(orderType)

    fun copy(orderType: OrderType): QuoteOrder {
        return when(this) {
            is By -> By(orderType)
     //       is Source -> By(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}