package com.smd.flexfuel.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldComponents(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange : (String) -> Unit,
    idLabel: Int,
    idPlaceHolder: Int
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().padding(60.dp, 0.dp),
        // REMOVIDO: .semantics { contentDescription = ... }
        // Deixe o sistema nativo lidar com campos de texto simples.
        value = value,
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        singleLine = true,
        label = { Text(text = stringResource(id = idLabel)) },
        placeholder = { Text(text = stringResource(id = idPlaceHolder)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
        )
    )
}