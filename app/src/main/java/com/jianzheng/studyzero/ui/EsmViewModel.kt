package com.jianzheng.studyzero.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.jianzheng.studyzero.data.EsmDatabase
import com.jianzheng.studyzero.data.EsmUiState
import com.jianzheng.studyzero.data.OfflineResponsesRepository
import com.jianzheng.studyzero.data.ResponsesRepository
import com.jianzheng.studyzero.data.toResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EsmViewModel(
    private val responsesRepository: ResponsesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EsmUiState(listOf(0, 0), false))
    val uiState: StateFlow<EsmUiState> = _uiState.asStateFlow()

    fun setAnswer(selection: Int, index: Int) {
        val updatedAnswers = uiState.value.answer.toMutableList()
        updatedAnswers[index] = selection
        //Log.d("viewModel", "$index is set to ${uiState.value.answer[index]}")
        _uiState.update { currentState ->
            currentState.copy(
                answer = updatedAnswers,
                isAnswerValid = (updatedAnswers[0] * updatedAnswers[1] != 0)
            )
        }
    }

    suspend fun saveAnswer(){
        responsesRepository.insertResponse(uiState.value.toResponse())
    }
    fun getAnswer(index: Int): Int {
        return uiState.value.answer[index]
    }

    fun resetAnswers() {
        Log.d("ViewModel", "Reset")
        _uiState.update { currentState ->
            currentState.copy(answer = listOf(0, 0))
        }
    }


}