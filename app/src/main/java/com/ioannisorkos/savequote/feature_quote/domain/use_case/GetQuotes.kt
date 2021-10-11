package com.ioannisorkos.savequote.feature_quote.domain.use_case

import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.repository.QuoteRepository
import com.ioannisorkos.savequote.feature_quote.domain.util.OrderType
import com.ioannisorkos.savequote.feature_quote.domain.util.QuoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetQuotes(
    private val repository: QuoteRepository
) {

    operator fun invoke(
        quoteOrder: QuoteOrder = QuoteOrder.Date(OrderType.Descending)
    ): Flow<List<Quote>> {
        return repository.getQuotes().map { notes ->
            when(quoteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(quoteOrder) {
                        is QuoteOrder.By -> notes.sortedBy { it.by.lowercase() }
                        is QuoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is QuoteOrder.Color -> notes.sortedBy { it.color }
                        is QuoteOrder.Source -> notes.sortedBy { it.source }
                    }
                }
                is OrderType.Descending -> {
                    when(quoteOrder) {
                        is QuoteOrder.By -> notes.sortedByDescending { it.by.lowercase() }
                        is QuoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is QuoteOrder.Color -> notes.sortedByDescending { it.color }
                        is QuoteOrder.Source -> notes.sortedBy { it.source }
                    }
                }
            }
        }
    }
}