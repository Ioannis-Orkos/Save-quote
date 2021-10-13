package com.ioannisorkos.savequote.feature_quote.presentation.quotes

import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.util.OrderType
import com.ioannisorkos.savequote.feature_quote.domain.util.QuoteOrder

data class QuotesState(
    val quotes: List<Quote> = emptyList(),
    val quoteOrder: QuoteOrder = QuoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isDialogSectionVisible: Boolean = false
)
