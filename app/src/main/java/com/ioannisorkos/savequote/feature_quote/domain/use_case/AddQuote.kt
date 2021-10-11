package com.ioannisorkos.savequote.feature_quote.domain.use_case

import com.ioannisorkos.savequote.feature_quote.domain.model.InvalidNoteException
import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.repository.QuoteRepository

class AddQuote(
    private val repository:QuoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(quote: Quote) {
        if(quote.by.isBlank()) {
            throw InvalidNoteException("The owner of the quote can't be empty.")
        }
        if(quote.source.isBlank()) {
            throw InvalidNoteException("The source of the quote can't be empty.")
        }
        if(quote.content.isBlank()) {
            throw InvalidNoteException("The content of the quote can't be empty.")
        }
        repository.insertQuote(quote)
    }

}