package com.ioannisorkos.savequote.feature_quote.presentation.add_edit_quotes

import androidx.compose.ui.focus.FocusState

sealed class AddEditQuoteEvent{
    data class EnteredBy(val value: String): AddEditQuoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditQuoteEvent()
    data class EnteredContent(val value: String): AddEditQuoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditQuoteEvent()
    data class ChangeColor(val color: Int): AddEditQuoteEvent()
    object SaveNote: AddEditQuoteEvent()
}

