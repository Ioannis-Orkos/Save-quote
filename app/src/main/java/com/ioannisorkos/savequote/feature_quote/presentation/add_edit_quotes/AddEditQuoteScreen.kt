package com.ioannisorkos.savequote.feature_quote.presentation.add_edit_quotes

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import com.ioannisorkos.savequote.feature_quote.presentation.add_edit_quotes.componentes.TransparentHintTextField
import com.ioannisorkos.savequote.feature_quote.presentation.util.QuoteArgState
import kotlinx.coroutines.flow.collectLatest

import kotlinx.coroutines.launch



@Composable
fun AddEditQuoteScreen(
    quoteArgState: QuoteArgState = QuoteArgState(),
    viewModel: AddEditQuoteViewModel = hiltViewModel(),
    onDialogOpen: (Boolean) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()

) {
//    SideEffect {
//        if (quoteArgState.id != -1) {
//            viewModel.onArgPass(quoteArgState)
//        }
//    }


    val quoteState = viewModel.quoteBy.value
    val contentState = viewModel.quoteContent.value

    val scope = rememberCoroutineScope()
    //val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher


    val noteBackgroundAnimatable = remember {
        Animatable(
            Color( viewModel.quoteColor.value)
        )
    }




    LaunchedEffect(key1 = true) {

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditQuoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message

                    )

                }
                is AddEditQuoteViewModel.UiEvent.SaveQuote -> {
                    // navController.navigateUp()
                }

            }
        }
    }


    Column(
        modifier = Modifier
            // .fillMaxHeight(0.7f)
            .fillMaxWidth(0.9f)

            .background(noteBackgroundAnimatable.value)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Quote.noteColors.forEach { color ->
                val colorInt = color.toArgb()
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = 3.dp,
                            color = if (viewModel.quoteColor.value == colorInt) {
                                Color.Black
                            } else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                noteBackgroundAnimatable.animateTo(
                                    targetValue = Color(colorInt),
                                    animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )
                            }
                            viewModel.onEvent(AddEditQuoteEvent.ChangeColor(colorInt))
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = contentState.text,
            hint = contentState.hint,
            onValueChange = {
                viewModel.onEvent(AddEditQuoteEvent.EnteredContent(it))
            },
            onFocusChange = {
                viewModel.onEvent(AddEditQuoteEvent.ChangeContentFocus(it))
            },
            isHintVisible = contentState.isHintVisible,
            textStyle = MaterialTheme.typography.h5,
            //modifier = Modifier.fillMaxHeight(0.f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = quoteState.text,
            hint = quoteState.hint,
            onValueChange = {
                viewModel.onEvent(AddEditQuoteEvent.EnteredBy(it))
            },
            onFocusChange = {
                viewModel.onEvent(AddEditQuoteEvent.ChangeTitleFocus(it))
            },
            isHintVisible = quoteState.isHintVisible,
            singleLine = true,
            //textStyle = MaterialTheme.typography.body1,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            textAlign = TextAlign.Right

        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onEvent(AddEditQuoteEvent.SaveNote)
                        onDialogOpen(false)
                },
            modifier = Modifier.align(alignment = Alignment.End),
        ) {
            Text(
                text = "Save"

            )
        }

    }
}



//}