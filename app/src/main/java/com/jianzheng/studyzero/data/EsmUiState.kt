package com.jianzheng.studyzero.data

data class EsmUiState(
    val answer: List<Int> = mutableListOf<Int>(),
    val isAnswerValid: Boolean
)

fun EsmUiState.toResponse(startingTime: Long): Response = Response(
    id = 0,
    delay = 0,
    answer1 = answer[0],
    answer2 = answer[1],
    startingTime = startingTime,
    submittingTime = System.currentTimeMillis()
)
