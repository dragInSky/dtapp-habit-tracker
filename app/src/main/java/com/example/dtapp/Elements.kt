package com.example.dtapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.dtapp.ui.theme.AppColors

@Composable
fun Spinner(
    text: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.clickable { expanded = true },
    ) {
        Column {
            Spacer(modifier = Modifier.height(12.dp))

            Box {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = text)

                    Box {
                        Text(text = selectedItem)

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(AppColors.WhiteGhost)
                        ) {
                            items.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it) },
                                    onClick = {
                                        onItemSelected(it)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Icon(Icons.Filled.ArrowDropDown, "Spinner arrow down")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}

@Composable
fun RadioButtons(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Column(Modifier.selectableGroup()) {
        items.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedItem),
                        onClick = { onItemSelected(text) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedItem),
                    onClick = { onItemSelected(text) }
                )
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

    OutlinedTextField(
        value = text,
        modifier = modifier,
        onValueChange = { onTextChanged(it) },
        placeholder = { Text(text = placeHolder) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        )
    )
}