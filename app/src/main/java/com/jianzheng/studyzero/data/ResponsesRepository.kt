package com.jianzheng.studyzero.data

interface ResponsesRepository {
    suspend fun insertResponse(response: Response)
}