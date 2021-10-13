package com.ioannisorkos.savequote.feature_quote.presentation.quotes

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ioannisorkos.savequote.feature_quote.presentation.add_edit_quotes.AddEditQuoteScreen
import com.ioannisorkos.savequote.feature_quote.presentation.add_edit_quotes.AddEditQuoteViewModel
import com.ioannisorkos.savequote.feature_quote.presentation.util.QuoteArgState
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NoteItem
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.OrderSection
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
//navController: NavController,
fun QuotesScreen(

    viewModel: QuotesViewModel= hiltViewModel(),
    viewModelAdd: AddEditQuoteViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()



//    AnimatedVisibility(
//        visible = state.isDialogSectionVisible,
//        enter = fadeIn() + slideInVertically(),
//        // exit = fadeOut() + slideOutVertically()
//    ) {
////        onDialogOpen(openDialog)
////    }
//

    if(state.isDialogSectionVisible){
        Dialog(onDismissRequest = {
            viewModel.onEvent(QuotesEvent.ToggleDialogSection(QuoteArgState()))},
            properties = DialogProperties(usePlatformDefaultWidth = false),
        )
        {
            AddEditQuoteScreen(viewModel = viewModelAdd,
                               quoteArgState = viewModel.argState.value,
                               onDialogOpen = { viewModel.onEvent(QuotesEvent.ToggleDialogSection(QuoteArgState()))})
            //viewModelAdd.onArgPass(QuoteArgState())

        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModelAdd.onArgPass(QuoteArgState())
                    viewModel.onEvent(QuotesEvent.ToggleDialogSection(QuoteArgState()))

                    //AddEditQuoteDialogScreen()
                  //  navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add quote")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your quote",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(QuotesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
               // exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.quoteOrder,
                    onOrderChange = {
                        viewModel.onEvent(QuotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxSize().animateContentSize(
                tween(50),
                    )) {
                items(state.quotes) { note ->
                    NoteItem(
                        quote = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                viewModelAdd.onArgPass((QuoteArgState(
                                    id = note.id?:-1, color = note.color?:-1)))
                                viewModel.onEvent(QuotesEvent.ToggleDialogSection(QuoteArgState(
                                    id = note.id?:-1, color = note.color?:-1)))
//                                navController.navigate(
//                                    Screen.AddEditNoteScreen.route +
//                                            "?noteId=${note.id}&noteColor=${note.color}"
//                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(QuotesEvent.DeleteQuote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Quote deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(QuotesEvent.RestoreQuote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}