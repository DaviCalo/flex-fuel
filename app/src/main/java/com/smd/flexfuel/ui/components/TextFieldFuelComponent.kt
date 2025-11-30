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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.smd.flexfuel.utils.RealVisualTransformation

@Composable
fun TextFieldFuelComponents(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange : (TextFieldValue) -> Unit,
    idLabel: Int,
){
    val labelText = stringResource(id = idLabel)

    // LÓGICA DE FORMATAÇÃO: Precisamos recriar o que o VisualTransformation faz
    // Se estiver vazio ou zero, o visual é "R$ 0,00"
    // Nota: Essa é uma formatação simplificada para bater com o visual.
    // O ideal seria usar a mesma função de formatação do resto do app.
    val valorVisual = if (value.text.isEmpty()) "0,00" else {
        try {
            val doubleValue = value.text.toDouble() / 100
            "%.2f".format(java.util.Locale("pt", "BR"), doubleValue)
        } catch (e: Exception) {
            "0,00"
        }
    }

    val textoCompletoParaLeitor = "$labelText R$ $valorVisual"

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(60.dp, 0.dp)
            .semantics {
                // AGORA SIM: A descrição contém "R$ 10,00", igual ao visual.
                contentDescription = textoCompletoParaLeitor
            },
        value = value,
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        singleLine = true,
        label = { Text(text = labelText) },
        placeholder = { Text(text = "R$ 0,00") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        ),
        visualTransformation = RealVisualTransformation()
    )
}