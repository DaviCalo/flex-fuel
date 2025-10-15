package com.smd.flexfuel.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun CalculateDialogComponents(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    idDialogTitle: Int,
    idDialogText: Int,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = stringResource(id = idDialogTitle))
        },
        text = {
            Text(text = stringResource(id = idDialogText))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
    )
}
