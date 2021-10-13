package com.ioannisorkos.savequote.feature_quote.presentation.quotes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.domain.use_case.QuoteUseCases
import com.ioannisorkos.savequote.feature_quote.domain.util.OrderType
import com.ioannisorkos.savequote.feature_quote.domain.util.QuoteOrder
import com.ioannisorkos.savequote.feature_quote.presentation.util.QuoteArgState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(QuotesState())
    val state: State<QuotesState> = _state

    private val _argState = mutableStateOf(QuoteArgState())
    val argState: State<QuoteArgState> = _argState

    private var recentlyDeletedQuote: Quote? = null

    private var getNotesJob: Job? = null


    init {
        getNotes(QuoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: QuotesEvent) {
        when (event) {
            is QuotesEvent.Order -> {
                if (state. value.quoteOrder::class == event.quoteOrder::class &&
                    state.value.quoteOrder.orderType == event.quoteOrder.orderType
                ) {
                    return
                }
                getNotes(event.quoteOrder)
            }
            is QuotesEvent.DeleteQuote -> {
                viewModelScope.launch {
                    quoteUseCases.deleteQuote(event.quote)
                    recentlyDeletedQuote = event.quote
                }
            }
            is QuotesEvent.RestoreQuote -> {
                viewModelScope.launch {
                    quoteUseCases.addQuote(recentlyDeletedQuote ?: return@launch)
                    recentlyDeletedQuote = null
                }
            }
            is QuotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is QuotesEvent.ToggleDialogSection -> {
                _argState.value = event.quoteArgState

                _state.value = state.value.copy(
                    isDialogSectionVisible = !state.value.isDialogSectionVisible
                )
            }
        }
    }

    private fun getNotes(quoteOrder: QuoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = quoteUseCases.getQuotes(quoteOrder)
            .onEach { quotes ->
                _state.value = state.value.copy(
                    quotes = quotes,
                    quoteOrder = quoteOrder
                )
            }
            .launchIn(viewModelScope)
    }


}