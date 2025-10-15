package com.smd.flexfuel.ui.utils

import androidx.compose.ui.text.input.OffsetMapping

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