package com.smd.flexfuel.ui.screens

import com.smd.flexfuel.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.smd.flexfuel.ui.components.TextFieldComponents
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smd.flexfuel.viewmodel.MainScreenViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smd.flexfuel.ui.components.ButtonComponent
import com.smd.flexfuel.ui.components.CalculateDialogComponents
import com.smd.flexfuel.ui.components.SwitchComponent
import com.smd.flexfuel.ui.theme.FlexFuelTheme
import com.smd.flexfuel.ui.utils.OptionFuel

@Composable
fun MainScreen(
    innerPadding: PaddingValues,
    viewModel: MainScreenViewModel = viewModel()
) {
    val alcoholInput by viewModel.alcoholValue.collectAsState()
    val gasolineInput by viewModel.gasolineValue.collectAsState()
    val isRatio70 by viewModel.isRatio70.collectAsState()
    val bestFuel by viewModel.bestFuel.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        TextFieldComponents(
            modifier = Modifier,
            value = alcoholInput,
            idLabel = R.string.alcohol,
            onValueChange = { newValue ->
                val newText = newValue.text.filter { it.isDigit() }
                val updatedValue = newValue.copy(
                    text = newText,
                    selection = TextRange(newText.length)
                )
                viewModel.onAlcoholValueChange(updatedValue)
            }
        )
        TextFieldComponents(
            modifier = Modifier,
            value = gasolineInput,
            idLabel = R.string.gasoline,
            onValueChange = { newValue ->
                val newText = newValue.text.filter { it.isDigit() }
                val updatedValue = newValue.copy(
                    text = newText,
                    selection = TextRange(newText.length)
                )
                viewModel.onGasolineValueChange(updatedValue)
            }
        )
        Row {
            Text("70%")
            SwitchComponent(
                checked = isRatio70,
                onCheckedChange = {
                    viewModel.onRatioChange(it)
                }
            )
            Text("75%")
        }
        ButtonComponent(
            onClick = {
                if(alcoholInput.text.isNotBlank() || gasolineInput.text.isNotBlank())
                    viewModel.calculateResult()

                openAlertDialog.value = true
            }
        )
    }
    when {
        openAlertDialog.value -> {
            CalculateDialogComponents(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                },
                dialogTitle = if(alcoholInput.text.isEmpty() || gasolineInput.text.isEmpty()) "Preencha os campos"
                                else "O melhor combustível é:",
                dialogText = when (bestFuel) {
                    OptionFuel.ALCOHOL -> "Álcool"
                    OptionFuel.GASOLINE -> "Gasolina"
                    else -> ""
                },
                icon = Icons.Default.Info
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    FlexFuelTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(innerPadding)
        }
    }
}