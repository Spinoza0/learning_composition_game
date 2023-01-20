package com.spinoza.compositiongame.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.compositiongame.domain.entity.Level
import com.spinoza.compositiongame.domain.repository.GameRepository

class GameViewModelFactory(
    private val application: Application,
    private val repository: GameRepository,
    private val level: Level,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return modelClass
                .getConstructor(Application::class.java,
                    GameRepository::class.java,
                    Level::class.java)
                .newInstance(application, repository, level)
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}