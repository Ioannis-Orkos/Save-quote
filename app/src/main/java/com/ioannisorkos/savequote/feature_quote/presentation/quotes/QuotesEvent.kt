package com.ioannisorkos.savequote.feature_quote.presentation.quotes

import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.util.QuoteOrder
import com.ioannisorkos.savequote.feature_quote.presentation.util.QuoteArgState

sealed class QuotesEvent {
    data class Order(val quoteOrder: QuoteOrder): QuotesEvent()
    data class DeleteQuote(val quote: Quote): QuotesEvent()
    object RestoreQuote: QuotesEvent()
    object ToggleOrderSection: QuotesEvent()
    data class ToggleDialogSection(val quoteArgState: QuoteArgState) : QuotesEvent()
}
