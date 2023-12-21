package com.jianzheng.studyzero.data

import android.content.Context

interface AppContainer {
    val responsesRepository: ResponsesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {

    override val responsesRepository: ResponsesRepository by lazy {
        OfflineResponsesRepository(EsmDatabase.getDatabase(context).responseDao())
    }
}