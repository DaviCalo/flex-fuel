package com.smd.flexfuel.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.smd.flexfuel.ui.utils.RealVisualTransformation

@Composable
fun TextFieldFuelComponents(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange : (TextFieldValue) -> Unit,
    idLabel: Int,
){
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().padding(60.dp, 0.dp),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        maxLines = 1,
        singleLine = true,
        label = { Text(text = stringResource(id = idLabel)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        ),
        visualTransformation = RealVisualTransformation()
    )
}