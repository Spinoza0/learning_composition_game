package com.spinoza.compositiongame.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.compositiongame.R
import com.spinoza.compositiongame.domain.entity.GameResult
import com.spinoza.compositiongame.domain.entity.GameSettings
import com.spinoza.compositiongame.domain.entity.Level
import com.spinoza.compositiongame.domain.entity.Question
import com.spinoza.compositiongame.domain.repository.GameRepository
import com.spinoza.compositiongame.domain.usecases.GenerateQuestionUseCase
import com.spinoza.compositiongame.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application, repository: GameRepository) :
    AndroidViewModel(application) {
    private val context = application
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level
    private var timer: CountDownTimer? = null

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _formattedProgressAnswers = MutableLiveData<String>()
    val formattedProgressAnswers: LiveData<String>
        get() = _formattedProgressAnswers

    private val _enoughCountORightAnswers = MutableLiveData<Boolean>()
    val enoughCountORightAnswers: LiveData<Boolean>
        get() = _enoughCountORightAnswers

    private val _enoughPercentORightAnswers = MutableLiveData<Boolean>()
    val enoughPercentORightAnswers: LiveData<Boolean>
        get() = _enoughPercentORightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame(level: Level) {
        getGameSettings(level)
        generateQuestion()
        startTimer()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        if (number == question.value?.rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _formattedProgressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountORightAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentORightAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return if (countOfRightAnswers == 0) {
            0
        } else {
            (countOfRightAnswers * 100.0 / countOfQuestions).toInt()
        }
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.durationInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millis: Long): String {
        val seconds = millis / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - minutes * SECONDS_IN_MINUTES
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountORightAnswers.value == true &&
                    enoughPercentORightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}