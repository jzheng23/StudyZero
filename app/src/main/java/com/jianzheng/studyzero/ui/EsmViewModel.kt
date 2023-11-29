package com.jianzheng.studyzero.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jianzheng.studyzero.data.EsmUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EsmViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EsmUiState(selection1 = 0, selection2 = 0))
    val uiState: StateFlow<EsmUiState> = _uiState.asStateFlow()


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

    fun getSelection1(): Int {
        return uiState.value.selection1
    }

    fun getSelection2(): Int {
        return uiState.value.selection2
    }

    fun resetSelections(){
        Log.d("ViewModel","Reset")
        _uiState.update { currentState ->
            currentState.copy(selection1 = 0, selection2 = 0)
        }
    }
}