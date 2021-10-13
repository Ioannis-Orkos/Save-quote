package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ioannisorkos.savequote.feature_quote.domain.util.OrderType
import com.ioannisorkos.savequote.feature_quote.domain.util.QuoteOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: QuoteOrder = QuoteOrder.Date(OrderType.Descending),
    onOrderChange: (QuoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "BY",
                selected = noteOrder is QuoteOrder.By,
                onSelect = { onOrderChange(QuoteOrder.By(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is QuoteOrder.Date,
                onSelect = { onOrderChange(QuoteOrder.Date(noteOrder.orderType)) }
            )
//            Spacer(modifier = Modifier.width(8.dp))
//            DefaultRadioButton(
//                text = "Color",
//                selected = noteOrder is QuoteOrder.Color,
//                onSelect = { onOrderChange(QuoteOrder.Color(noteOrder.orderType)) }
//            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}