package com.smd.flexfuel.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TextFieldComponents(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange : (TextFieldValue) -> Unit,
    idLabel: Int,
){
    OutlinedTextField(
        modifier = modifier,
        // Usa o objeto TextFieldValue
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        maxLines = 1,
        label = { Text(text = stringResource(id = idLabel)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        ),
        visualTransformation = RealVisualTransformation()
    )
}

class RealOffsetMapping(
    private val originalText: String,
    private val transformedText: String
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset == originalText.length) {
            return transformedText.length
        }
        if (originalText.isEmpty()) {
            return transformedText.length
        }
        val originalDigits = originalText.length
        val transformedDigits = transformedText.length - 4
        val transformedOffset = (offset.toFloat() / originalDigits * transformedDigits).toInt() + 3
        return minOf(transformedOffset, transformedText.length)
    }
    override fun transformedToOriginal(offset: Int): Int {
        val cleanOffset = offset.coerceIn(0, transformedText.length)
        if (cleanOffset >= transformedText.indexOf(',')) {
            return originalText.length
        }
        val originalOffset = cleanOffset - 3
        return maxOf(0, originalOffset)
    }
}

class RealVisualTransformation : VisualTransformation {
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
    override fun filter(text: AnnotatedString): TransformedText {
        val cleanedText = text.text.replace("[^\\d]".toRegex(), "")
        if (cleanedText.isEmpty()) {
            return TransformedText(AnnotatedString("R$ 0,00"), RealOffsetMapping(cleanedText, "R$ 0,00"))
        }
        val value = cleanedText.toBigDecimal().divide(100.toBigDecimal())
        val formattedText = formatter.format(value)
        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = RealOffsetMapping(cleanedText, formattedText)
        )
    }
}