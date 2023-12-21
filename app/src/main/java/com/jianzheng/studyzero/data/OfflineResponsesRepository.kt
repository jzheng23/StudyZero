package com.jianzheng.studyzero.data

class OfflineResponsesRepository(private val responseDao: ResponseDao): ResponsesRepository {
    override suspend fun insertResponse(response: Response) = responseDao.insert(response)

}