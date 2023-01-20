package com.spinoza.compositiongame.domain.repository

import com.spinoza.compositiongame.domain.entity.GameSettings
import com.spinoza.compositiongame.domain.entity.Level
import com.spinoza.compositiongame.domain.entity.Question

interface GameRepository {
    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
    fun getGameSettings(level: Level): GameSettings
}