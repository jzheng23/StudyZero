package com.jianzheng.studyzero.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jianzheng.studyzero.EsmApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            EsmViewModel(esmApplication().container.responsesRepository)
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [EsmApplication].
 */
fun CreationExtras.esmApplication(): EsmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EsmApplication)