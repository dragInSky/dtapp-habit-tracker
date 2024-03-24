package com.example.dtapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dtapp.ui.theme.AppColors
import com.example.dtapp.ui.theme.Purple40
import kotlinx.coroutines.launch

@Composable
fun CenterTextButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            onClick = { onClick() }, colors = ButtonDefaults.buttonColors(
                containerColor = Purple40, contentColor = Color.White
            )
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddButton(description: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier.padding(24.dp), contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { onClick() }, containerColor = Purple40, contentColor = Color.White
        ) {
            Icon(Icons.Filled.Add, description, tint = Color.White)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(pagerState: PagerState, pages: List<String>) {
    val scrollCoroutineScope = rememberCoroutineScope()

    Box {
        TabRow(
            selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color.Black
                )
            }, modifier = Modifier.align(Alignment.TopCenter), contentColor = Color.Black
        ) {
            pages.forEachIndexed { index, title ->
                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .clickable {
                        scrollCoroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                    .padding(8.dp)) {
                    Text(text = title)
                }
            }
        }
    }
}

@Composable
fun Spinner(
    text: String, items: List<String>, selectedItem: String, onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.clickable { expanded = true }) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = text)

            Box {
                Text(text = selectedItem)

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(AppColors.WhiteGhost)
                ) {
                    items.forEach {
                        DropdownMenuItem(text = { Text(text = it) }, onClick = {
                            onItemSelected(it)
                            expanded = false
                        })
                    }
                }
            }

            Icon(Icons.Filled.ArrowDropDown, "Spinner arrow down")
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun RadioButtons(
    items: List<String>, selectedItem: String, onItemSelected: (String) -> Unit
) {
    Column(Modifier.selectableGroup()) {
        items.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedItem),
                        onClick = { onItemSelected(text) }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = (text == selectedItem), onClick = { onItemSelected(text) })
                Text(text = text)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HidingTextField(
    text: String,
    placeHolder: String,
    modifier: Modifier,
    onTextChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        modifier = modifier,
        onValueChange = { onTextChanged(it) },
        placeholder = { Text(text = placeHolder) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            keyboardController?.hide()
        }),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White
        )
    )
}