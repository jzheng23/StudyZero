package com.jianzheng.studyzero.ui

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.jianzheng.studyzero.data.EsmUiState
import com.jianzheng.studyzero.data.Response
import com.jianzheng.studyzero.data.ResponseDao
import com.jianzheng.studyzero.data.toResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EsmViewModel(
    private val responseDao: ResponseDao
) {
    private val _uiState = MutableStateFlow(EsmUiState(listOf(0, 0), false))
    val uiState: StateFlow<EsmUiState> = _uiState.asStateFlow()
    private val database = Firebase.database
    private val myRef = database.getReference("ESM")

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

    suspend fun saveAnswer(delay: Long, startingTime: Long){
        val newResponse = uiState.value.toResponse(delay, startingTime)
        responseDao.insert(newResponse)
        uploadToFirebase(responseDao.getLastResponse())
        Log.d("data","data saved")
    }

    private fun uploadToFirebase(newResponse: Response) {
        myRef.setValue(newResponse)
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