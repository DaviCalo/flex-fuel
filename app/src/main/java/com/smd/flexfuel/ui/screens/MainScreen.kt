package com.smd.flexfuel.ui.screens

import com.smd.flexfuel.R
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.smd.flexfuel.ui.components.TextFieldComponents
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smd.flexfuel.viewmodel.MainScreenViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlin.getValue

@Composable
fun MainScreen(
    innerPadding: PaddingValues
) {
    val viewModel: MainScreenViewModel = viewModel()
    val alcoholInput by viewModel.alcoholValue.collectAsState()
    val gasolineInput by viewModel.gasolineValue.collectAsState()
    Column {
        TextFieldComponents(
            modifier = Modifier,
            defaultValue = alcoholInput,
            idLabel = R.string.alcohol,
            idPlaceholder = R.string.alcohol,
            onValueChange = { viewModel.onAlcoholValueChange(it) }
        )
        TextFieldComponents(
            modifier = Modifier,
            defaultValue = gasolineInput,
            idLabel = R.string.gasoline,
            idPlaceholder = R.string.gasoline,
            onValueChange = { viewModel.onGasolineValueChange(it) }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(innerPadding = PaddingValues())
}