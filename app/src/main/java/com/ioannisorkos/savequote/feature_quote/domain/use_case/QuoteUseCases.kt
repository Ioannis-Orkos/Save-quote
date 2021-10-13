package com.ioannisorkos.savequote.feature_quote.domain.use_case

data class QuoteUseCases(
    val getQuotes: GetQuotes,
    val deleteQuote: DeleteQuote,
    val addQuote: AddQuote,
    val getQuote: GetQuote
)
