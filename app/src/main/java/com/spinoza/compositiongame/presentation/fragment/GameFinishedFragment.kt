package com.spinoza.compositiongame.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spinoza.compositiongame.R
import com.spinoza.compositiongame.databinding.FragmentGameFinishedBinding
import com.spinoza.compositiongame.domain.entity.GameResult
import com.spinoza.compositiongame.presentation.calculatePercent

class GameFinishedFragment : Fragment() {
    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContent()
        setListeners()
    }

    private fun setListeners() {
        binding.buttonRetry.setOnClickListener { retryGame() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setContent() {
        with(binding) {
            imageViewEmojiResult.setImageResource(getSmileResId())

            textViewRequiredAnswers.text = String.format(getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers)

            textViewScoreAnswers.text =
                String.format(getString(R.string.score_answers), gameResult.countOfRightAnswers)

            textViewRequiredPercentage.text = String.format(getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers)

            textViewScorePercentage.text = String.format(getString(R.string.score_percentage),
                calculatePercent(gameResult.countOfRightAnswers, gameResult.countOfQuestions))
        }
    }

    private fun getSmileResId() = if (gameResult.winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }

    private fun parseArguments() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(GAME_RESULT, GameResult::class.java)?.let {
                gameResult = it
            }
        } else {
            @Suppress("deprecation")
            requireArguments().getParcelable<GameResult>(GAME_RESULT)?.let {
                gameResult = it
            }
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    companion object {
        const val GAME_RESULT = "gameResult"
    }
}