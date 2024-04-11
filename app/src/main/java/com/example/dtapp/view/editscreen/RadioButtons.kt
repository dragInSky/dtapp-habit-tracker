package com.example.dtapp.view.editscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
                        onClick = { onItemSelected(text) }),
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