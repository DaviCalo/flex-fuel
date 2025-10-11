package com.smd.flexfuel.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ButtonComponent(
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick }
    ) {
        Text("Calcular")
    }
}