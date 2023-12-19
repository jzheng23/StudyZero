package com.jianzheng.studyzero.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.jianzheng.studyzero.data.EsmUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EsmViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EsmUiState(listOf(0,0)))
    val uiState: StateFlow<EsmUiState> = _uiState.asStateFlow()

    fun setAnswer(selection: Int, index: Int){
        val updatedAnswers = uiState.value.answer.toMutableList()
        updatedAnswers[index] = selection
        _uiState.update { currentState ->
            currentState.copy(answer = updatedAnswers)
        }
        Log.d("viewModel","$index is set to $selection")
    }

    fun getAnswer(index: Int): Int {
        return uiState.value.answer[index]
    }

    fun resetAnswers() {
        Log.d("ViewModel", "Reset")
        _uiState.update { currentState ->
            currentState.copy(answer = listOf(0,0))
        }
    }

    fun validateAnswer(): Boolean{
        val productOfAnswer = uiState.value.answer[0] * uiState.value.answer[1]
        return productOfAnswer != 0
    }

}