package com.smd.flexfuel.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.smd.flexfuel.ui.utils.OptionFuel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenViewModel: ViewModel() {
    private val _gasolineValue = MutableStateFlow(TextFieldValue(""))
    val gasolineValue: StateFlow<TextFieldValue> = _gasolineValue.asStateFlow()

    private val _alcoholValue = MutableStateFlow(TextFieldValue())
    val alcoholValue: StateFlow<TextFieldValue> = _alcoholValue.asStateFlow()

    private val _isRatio70 = MutableStateFlow(false)
    val isRatio70: StateFlow<Boolean> = _isRatio70.asStateFlow()

    private val _bestFuel: MutableStateFlow<OptionFuel> = MutableStateFlow(OptionFuel.NONE)
    val bestFuel: StateFlow<OptionFuel> = _bestFuel.asStateFlow()

    fun onAlcoholValueChange(newValue: TextFieldValue) {
        _alcoholValue.update { newValue }
    }

    fun onGasolineValueChange(newValue: TextFieldValue) {
        _gasolineValue.update { newValue }
    }

    fun onRatioChange(newRatio: Boolean) {
        _isRatio70.update { newRatio }
    }

    fun onBestFuel(newValue: OptionFuel) {
        _bestFuel.update { newValue }
    }

    fun calculateResult() {
        if (_alcoholValue.value.text.isEmpty() || _gasolineValue.value.text.isEmpty())
            return
        val alcohol = _alcoholValue.value.toString().toDoubleOrNull() ?: 0.0
        val gasoline = _gasolineValue.value.toString().toDoubleOrNull() ?: 0.0
        val ratio = if (_isRatio70.value) 0.7 else 0.75
        if (alcohol >= (gasoline * ratio)) onBestFuel(OptionFuel.ALCOHOL) else onBestFuel(OptionFuel.GASOLINE)
    }
}