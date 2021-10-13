package com.ioannisorkos.savequote.feature_quote.presentation.add_edit_quotes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ioannisorkos.savequote.feature_quote.domain.model.InvalidNoteException
import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.use_case.QuoteUseCases
import com.ioannisorkos.savequote.feature_quote.presentation.util.QuoteArgState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditQuoteViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases,
) : ViewModel() {

    private val _quoteBy = mutableStateOf(QuoteTextFieldState(
        hint = "How said it"
    ))
    val quoteBy: State<QuoteTextFieldState> = _quoteBy

    private val _quoteContent = mutableStateOf(QuoteTextFieldState(
        hint = "\"Enter quote\""
    ))
    val quoteContent: State<QuoteTextFieldState> = _quoteContent

    private val _quoteSource = mutableStateOf(QuoteTextFieldState(
        hint = "Enter the source"
    ))
    val quoteSource: State<QuoteTextFieldState> = _quoteSource

    private val _quoteId = mutableStateOf(-1)
    val quoteId: State<Int> = _quoteId

    private val _quoteColor = mutableStateOf(Quote.noteColors.random().toArgb())
    val quoteColor: State<Int> = _quoteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

//    init {
//            if(quoteId.value != -1) {
//                populateQuote()
//            }
//
//    }

    private fun populateQuote(){
         viewModelScope.launch {
            quoteUseCases.getQuote(quoteId.value)?.also { note ->
                currentNoteId = note.id
                _quoteBy.value = quoteBy.value.copy(
                    text = note.by,
                    isHintVisible = false
                )
                _quoteContent.value = _quoteContent.value.copy(
                    text = note.content,
                    isHintVisible = false
                )
                _quoteColor.value = note.color
            }
        }
    }

    fun onArgPass(quoteArgState: QuoteArgState){

        if(quoteArgState.id != -1) {
            _quoteId.value = quoteArgState.id
            _quoteColor.value = quoteArgState.id
            populateQuote()
        }
        else{
            _quoteId.value =  quoteArgState.id
            _quoteColor.value =  Quote.noteColors.random().toArgb()

            currentNoteId = null
            _quoteContent.value = QuoteTextFieldState(
                hint = "Enter quote"
            )
            _quoteBy.value = QuoteTextFieldState(
                hint = "How said it"
            )
            _quoteSource.value = QuoteTextFieldState(
                hint = "Enter the source"
            )

        }

    }

    fun onEvent(event: AddEditQuoteEvent) {
        when(event) {
            is AddEditQuoteEvent.EnteredBy -> {
                _quoteBy.value = quoteBy.value.copy(
                    text = event.value
                )
            }
            is AddEditQuoteEvent.ChangeTitleFocus -> {
                _quoteBy.value = quoteBy.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            quoteBy.value.text.isBlank()
                )
            }
            is AddEditQuoteEvent.EnteredContent -> {
                _quoteContent.value = _quoteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditQuoteEvent.ChangeContentFocus -> {
                _quoteContent.value = _quoteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _quoteContent.value.text.isBlank()
                )
            }
            is AddEditQuoteEvent.ChangeColor -> {
                _quoteColor.value = event.color
            }
            is AddEditQuoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        quoteUseCases.addQuote(
                            Quote(
                                by = quoteBy.value.text,
                                content = quoteContent.value.text,
                                source = quoteSource.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = quoteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveQuote)
                    } catch(e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save quote"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveQuote: UiEvent()
    }
}