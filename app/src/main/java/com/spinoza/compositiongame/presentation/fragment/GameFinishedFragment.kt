package com.spinoza.compositiongame.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.spinoza.compositiongame.R
import com.spinoza.compositiongame.databinding.FragmentGameFinishedBinding
import com.spinoza.compositiongame.presentation.calculatePercent

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()
    private val gameResult by lazy { args.gameResult }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


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

    private fun retryGame() {
        findNavController().popBackStack()
    }
}