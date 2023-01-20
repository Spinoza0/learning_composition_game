package com.spinoza.compositiongame.data

import com.spinoza.compositiongame.domain.entity.GameSettings
import com.spinoza.compositiongame.domain.entity.Level
import com.spinoza.compositiongame.domain.entity.Question
import com.spinoza.compositiongame.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min

import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    private const val TEST_MAX_SUM_VALUE = 10
    private const val TEST_COUNT_OF_RIGHT_ANSWERS = 3
    private const val TEST_PERCENT_OF_RIGHT_ANSWERS = 50
    private const val TEST_DURATION_IN_SECONDS = 8

    private const val EASY_MAX_SUM_VALUE = 10
    private const val EASY_COUNT_OF_RIGHT_ANSWERS = 10
    private const val EASY_PERCENT_OF_RIGHT_ANSWERS = 70
    private const val EASY_DURATION_IN_SECONDS = 60

    private const val NORMAL_MAX_SUM_VALUE = 20
    private const val NORMAL_COUNT_OF_RIGHT_ANSWERS = 20
    private const val NORMAL_PERCENT_OF_RIGHT_ANSWERS = 80
    private const val NORMAL_DURATION_IN_SECONDS = 40

    private const val HARD_MAX_SUM_VALUE = 30
    private const val HARD_COUNT_OF_RIGHT_ANSWERS = 30
    private const val HARD_PERCENT_OF_RIGHT_ANSWERS = 90
    private const val HARD_DURATION_IN_SECONDS = 40

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(rightAnswer + countOfOptions, maxSumValue)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(
                TEST_MAX_SUM_VALUE,
                TEST_COUNT_OF_RIGHT_ANSWERS,
                TEST_PERCENT_OF_RIGHT_ANSWERS,
                TEST_DURATION_IN_SECONDS
            )
            Level.EASY -> GameSettings(
                EASY_MAX_SUM_VALUE,
                EASY_COUNT_OF_RIGHT_ANSWERS,
                EASY_PERCENT_OF_RIGHT_ANSWERS,
                EASY_DURATION_IN_SECONDS
            )
            Level.NORMAL -> GameSettings(
                NORMAL_MAX_SUM_VALUE,
                NORMAL_COUNT_OF_RIGHT_ANSWERS,
                NORMAL_PERCENT_OF_RIGHT_ANSWERS,
                NORMAL_DURATION_IN_SECONDS
            )
            Level.HARD -> GameSettings(
                HARD_MAX_SUM_VALUE,
                HARD_COUNT_OF_RIGHT_ANSWERS,
                HARD_PERCENT_OF_RIGHT_ANSWERS,
                HARD_DURATION_IN_SECONDS
            )
        }
    }
}