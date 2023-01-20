package com.spinoza.compositiongame.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.compositiongame.domain.repository.GameRepository

class GameViewModelFactory(
    private val application: Application,
    private val repository: GameRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Application::class.java, GameRepository::class.java)
            .newInstance(application, repository)
    }
}