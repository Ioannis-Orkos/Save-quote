package com.ioannisorkos.savequote.feature_quote.presentation.add_edit_quotes

data class QuoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
