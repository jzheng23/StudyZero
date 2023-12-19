package com.jianzheng.studyzero.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jianzheng.studyzero.data.EsmUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EsmViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EsmUiState())
    val uiState: StateFlow<EsmUiState> = _uiState.asStateFlow()

    fun setAnswer(selection: Int, index: Int){
        val updatedAnswers = uiState.value.answer.toMutableList()
        updatedAnswers[index] = selection
        _uiState.update { currentState ->
            currentState.copy(answer = updatedAnswers)
        }
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

}