package com.ioannisorkos.savequote.feature_quote.domain.use_case

import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.repository.QuoteRepository

class GetQuote(
    private val repository: QuoteRepository
) {

    suspend operator fun invoke(id: Int): Quote? {
        return repository.getQuoteById(id)
    }
}