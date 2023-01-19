package com.spinoza.compositiongame.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val durationInSeconds: Int,
): java.io.Serializable