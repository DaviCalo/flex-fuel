package com.smd.flexfuel.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TextFieldComponents(
    modifier: Modifier = Modifier,
    defaultValue: String,
    onValueChange : (String) -> Unit,
    idLabel: Int,
    idPlaceholder: Int,
){
    OutlinedTextField(
        modifier = modifier,
        value = defaultValue,
        onValueChange = {
            onValueChange(it)
        },
        maxLines = 1,
        label = { Text(text = stringResource(id = idLabel)) },
        placeholder = { Text(text = stringResource(id = idPlaceholder)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        )
    )
}
