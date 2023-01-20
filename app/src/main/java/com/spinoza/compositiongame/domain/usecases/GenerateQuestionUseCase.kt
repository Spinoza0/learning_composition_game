package com.spinoza.compositiongame.domain.usecases

import com.spinoza.compositiongame.domain.entity.Question
import com.spinoza.compositiongame.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        const val COUNT_OF_OPTIONS = 6
    }
}