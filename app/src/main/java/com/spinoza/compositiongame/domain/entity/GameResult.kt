package com.spinoza.compositiongame.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings,
) : java.io.Serializable