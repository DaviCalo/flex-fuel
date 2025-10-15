package com.smd.flexfuel.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

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