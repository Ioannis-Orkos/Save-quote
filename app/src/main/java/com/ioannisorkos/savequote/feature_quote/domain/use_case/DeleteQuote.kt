package com.ioannisorkos.savequote.feature_quote.domain.use_case

import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.repository.QuoteRepository


class DeleteQuote(
    private val repository: QuoteRepository
) {

    suspend operator fun invoke(quote: Quote) {
        repository.deleteQuote(quote)
    }
}