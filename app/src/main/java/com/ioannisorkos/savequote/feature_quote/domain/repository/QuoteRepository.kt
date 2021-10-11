package com.ioannisorkos.savequote.feature_quote.domain.repository

import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun getQuotes(): Flow<List<Quote>>

    suspend fun getQuoteById(id: Int): Quote?

    suspend fun insertQuote(quote: Quote)

    suspend fun deleteQuote(quote: Quote)

}