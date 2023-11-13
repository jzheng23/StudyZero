package com.jianzheng.studyzero.ui

import androidx.lifecycle.ViewModel
import com.jianzheng.studyzero.data.EsmUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EsmViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EsmUiState(selection1 = 0, selection2 = 0))
    val uiState: StateFlow<EsmUiState> = _uiState.asStateFlow()
    fun exitApp(){
        
    }

    fun setSelection1(selection: Int = 0) {
        _uiState.update { currentState ->
            currentState.copy(selection1 = selection)
        }
    }

    fun setSelection2(selection: Int = 0) {
        _uiState.update { currentState ->
            currentState.copy(selection2 = selection)
        }
    }
}