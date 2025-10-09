package com.smd.flexfuel.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenViewModel: ViewModel() {
    private val _gasolineValue = MutableStateFlow("")
    val gasolineValue: StateFlow<String> = _gasolineValue.asStateFlow()

    // Para o valor do Álcool
    private val _alcoholValue = MutableStateFlow("")
    val alcoholValue: StateFlow<String> = _alcoholValue.asStateFlow()

    private val _isRatio70 = MutableStateFlow(true)
    val isRatio70: StateFlow<Boolean> = _isRatio70.asStateFlow()

    fun onAlcoholValueChange(newValue: String) {
        _alcoholValue.update { newValue }
    }

    fun onGasolineValueChange(newValue: String) {
        _gasolineValue.update { newValue }
    }

    fun onRatioChange(newRatio: Boolean) {
        _isRatio70.update { newRatio }
    }
}