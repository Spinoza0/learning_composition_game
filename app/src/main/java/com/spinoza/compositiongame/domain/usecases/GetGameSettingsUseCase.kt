package com.spinoza.compositiongame.domain.usecases

import com.spinoza.compositiongame.domain.entity.GameSettings
import com.spinoza.compositiongame.domain.entity.Level
import com.spinoza.compositiongame.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings = repository.getGameSettings(level)
}