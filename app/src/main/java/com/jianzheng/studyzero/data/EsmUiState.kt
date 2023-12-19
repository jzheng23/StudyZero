package com.jianzheng.studyzero.data

data class EsmUiState(
    val answer: List<Int> = mutableListOf<Int>(),
    val isAnswerValid: Boolean
)
