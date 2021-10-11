package com.ioannisorkos.savequote.feature_quote.data.repository

import com.ioannisorkos.savequote.feature_quote.data.data_source.QuoteDao
import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class QuoteRepositoryImpl(
    private val dao:QuoteDao
): QuoteRepository {

    override fun getQuotes(): Flow<List<Quote>> {
        return dao.getNotes()
    }

    override suspend fun getQuoteById(id: Int): Quote? {
        return dao.getNoteById(id)
    }

    override suspend fun insertQuote(quote: Quote) {
        return dao.insertNote(quote )
    }

    override suspend fun deleteQuote(quote: Quote) {
        return dao.deleteNote(quote)
    }

}