package com.smd.flexfuel.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.smd.flexfuel.R
import com.smd.flexfuel.ui.components.ButtonComponent
import com.smd.flexfuel.ui.components.CalculateDialogComponents
import com.smd.flexfuel.ui.components.SwitchComponent
import com.smd.flexfuel.ui.components.TextFieldComponents
import com.smd.flexfuel.ui.components.TextFieldFuelComponents
import com.smd.flexfuel.utils.OptionFuel
import com.smd.flexfuel.viewmodel.EditeDataViewModel


import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditeDataScreen(
    navController: NavHostController,
    idPost: Int?,
    viewModel: EditeDataViewModel = viewModel()
) {
    val context = LocalContext.current
    viewModel.initSharedPrefsManager(context = context)

    androidx.compose.runtime.LaunchedEffect(idPost) {
        if (idPost != null) {
            viewModel.loadPostData(idPost)
        }
    }

    val alcoholInput by viewModel.alcoholValue.collectAsState()
    val gasolineInput by viewModel.gasolineValue.collectAsState()
    val isRatio70 by viewModel.isRatio70.collectAsState()
    val bestFuel by viewModel.bestFuel.collectAsState()
    val gasStation by viewModel.gasStation.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.edit_post))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (idPost != null) {
                                viewModel.deletePost(idPost)
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                actions = {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onClick = {
                            if (alcoholInput.text.isNotBlank() || gasolineInput.text.isNotBlank() || gasStation.isNotBlank()) {
                                if (idPost != null) {
                                    viewModel.updatePost(idPost) // Chama a função de editar/salvar
                                }
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Text(stringResource(R.string.save_post))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            TextFieldFuelComponents(
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
            TextFieldFuelComponents(
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
            TextFieldComponents(
                modifier = Modifier,
                value = gasStation,
                idLabel = R.string.label_gas_station,
                idPlaceHolder = R.string.placeholder_gas_station,
                onValueChange = {
                    viewModel.onGasStationChange(it)
                },
            )
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                modifier = Modifier,
                onClick = {
                    if (alcoholInput.text.isNotBlank() || gasolineInput.text.isNotBlank())
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
                    idDialogTitle = if (alcoholInput.text.isEmpty() || gasolineInput.text.isEmpty()) R.string.error_fields
                    else R.string.result,
                    idDialogText = when (bestFuel) {
                        OptionFuel.ALCOHOL -> R.string.alcohol
                        OptionFuel.GASOLINE -> R.string.gasoline
                        else -> R.string.error_gas_station
                    },
                    icon = Icons.Default.Info
                )
            }
        }
    }
}