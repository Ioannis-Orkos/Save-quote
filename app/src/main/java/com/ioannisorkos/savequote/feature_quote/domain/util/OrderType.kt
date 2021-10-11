package com.ioannisorkos.savequote.feature_quote.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
