package com.smd.flexfuel.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.smd.flexfuel.model.Post
import com.smd.flexfuel.utils.OptionFuel
import com.smd.flexfuel.utils.SharedPrefsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale

class EditeDataViewModel: ViewModel() {
    private val _gasolineValue = MutableStateFlow(TextFieldValue(""))
    val gasolineValue: StateFlow<TextFieldValue> = _gasolineValue.asStateFlow()

    private val _alcoholValue = MutableStateFlow(TextFieldValue())
    val alcoholValue: StateFlow<TextFieldValue> = _alcoholValue.asStateFlow()

    private val _isRatio70 = MutableStateFlow(false)
    val isRatio70: StateFlow<Boolean> = _isRatio70.asStateFlow()

    private val _bestFuel: MutableStateFlow<OptionFuel> = MutableStateFlow(OptionFuel.NONE)
    val bestFuel: StateFlow<OptionFuel> = _bestFuel.asStateFlow()

    private val _gasStation: MutableStateFlow<String> = MutableStateFlow("")
    val gasStation: StateFlow<String> = _gasStation.asStateFlow()

    var sharedPrefsManager: SharedPrefsManager? = null

    fun initSharedPrefsManager(context: Context) {
        sharedPrefsManager = SharedPrefsManager(context)
    }

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

    fun onGasStationChange(newValue: String) {
        _gasStation.update { newValue }
    }

    /*
    fun calculateResult() {
        if (_alcoholValue.value.text.isEmpty() || _gasolineValue.value.text.isEmpty())
            return
        val alcohol = _alcoholValue.value.toString().toDoubleOrNull() ?: 0.0
        val gasoline = _gasolineValue.value.toString().toDoubleOrNull() ?: 0.0
        val ratio = if (_isRatio70.value) 0.7 else 0.75
        if (alcohol <= (gasoline * ratio)) {
            onBestFuel(OptionFuel.ALCOHOL)
        } else {
            onBestFuel(OptionFuel.GASOLINE)
        }
    }*/

    fun calculateResult() {
        val alcoholText = _alcoholValue.value.text
        val gasolineText = _gasolineValue.value.text

        if (alcoholText.isEmpty() || gasolineText.isEmpty())
            return

        val alcohol = convertCommaStringToDouble(alcoholText)
        val gasoline = convertCommaStringToDouble(gasolineText)

        val ratio = if (_isRatio70.value) 0.7 else 0.75

        if (alcohol <= (gasoline * ratio)) {
            onBestFuel(OptionFuel.ALCOHOL)
        } else {
            onBestFuel(OptionFuel.GASOLINE)
        }
    }

    fun convertCommaStringToDouble(value: String): Double {
        val cleanValue = value.replace(Regex("[^0-9]"), "")
        val formattedValue = if (cleanValue.length < 3) {
            "0," + cleanValue.padStart(2, '0')
        } else {
            cleanValue.substring(0, cleanValue.length - 2) +
                    "," +
                    cleanValue.substring(cleanValue.length - 2)
        }
        val format = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"))

        return try {
            format.parse(formattedValue)?.toDouble() ?: 0.0
        } catch (e: Exception) {
            e.printStackTrace()
            0.0
        }
    }

    /*
    fun editedPost() {
        val manager = sharedPrefsManager ?: return
        val gasolineText = _gasolineValue.value.text
        val alcoholText = _alcoholValue.value.text
        val gasolineDouble = convertCommaStringToDouble(gasolineText)
        val alcoholDouble = convertCommaStringToDouble(alcoholText)
        // TODO: CHAME A FUNCAO PARA EDITAR AQUI
    }
    */


    fun updatePost(idPost: Int) {
        val manager = sharedPrefsManager ?: return
        val gasolineText = _gasolineValue.value.text
        val alcoholText = _alcoholValue.value.text
        val gasolineDouble = convertCommaStringToDouble(gasolineText)
        val alcoholDouble = convertCommaStringToDouble(alcoholText)


        manager.updatePost(
            Post(
                id = idPost,
                name = _gasStation.value,
                gasolineValue = gasolineDouble,
                alcoholValue = alcoholDouble,
                isRatio70 = _isRatio70.value,
                location = null // Manteremos null até a próxima task
            )
        )
    }




    fun loadPostData(idPost: Int) {
        val manager = sharedPrefsManager ?: return
        val post = manager.getPost(idPost) ?: return

        // Converte Double (ex: 5.59) para String limpa (ex: "559") para o visual transformation
        val gasString = String.format(Locale("pt", "BR"), "%.2f", post.gasolineValue).replace(Regex("[^0-9]"), "")
        val alcString = String.format(Locale("pt", "BR"), "%.2f", post.alcoholValue).replace(Regex("[^0-9]"), "")

        _gasolineValue.update { TextFieldValue(text = gasString, selection = androidx.compose.ui.text.TextRange(gasString.length)) }
        _alcoholValue.update { TextFieldValue(text = alcString, selection = androidx.compose.ui.text.TextRange(alcString.length)) }
        _gasStation.update { post.name }
        _isRatio70.update { post.isRatio70 }

        calculateResult() // Recalcula qual é o melhor combustível com os dados carregados
    }

    fun deletePost(idPost: Int) {
        sharedPrefsManager?.deletePost(idPost)
    }
}