package com.spinoza.compositiongame.presentation.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.spinoza.compositiongame.data.GameRepositoryImpl
import com.spinoza.compositiongame.databinding.FragmentGameBinding
import com.spinoza.compositiongame.domain.entity.GameResult
import com.spinoza.compositiongame.presentation.viewmodel.GameViewModel
import com.spinoza.compositiongame.presentation.viewmodel.GameViewModelFactory
import kotlin.random.Random

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory: GameViewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, GameRepositoryImpl, args.level)
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val textViewOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.textViewOption1)
            add(binding.textViewOption2)
            add(binding.textViewOption3)
            add(binding.textViewOption4)
            add(binding.textViewOption5)
            add(binding.textViewOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        textViewOptions.forEach { textView ->
            textView.setOnClickListener {
                textView.text?.let { viewModel.chooseAnswer(it.toString().toInt()) }
            }
        }
    }

    private fun setObservers() {
        with(binding) {
            viewModel.formattedTime.observe(viewLifecycleOwner) { textViewTimer.text = it }
            viewModel.question.observe(viewLifecycleOwner) {
                textViewSum.text = it.sum.toString()
                textViewLeftNumber.text = it.visibleNumber.toString()
                val options = ArrayList<Int>(it.options)
                while (options.size > 0) {
                    textViewOptions[options.size - 1].text = getOption(options)
                }
            }
            viewModel.formattedProgressAnswers.observe(viewLifecycleOwner) {
                textViewAnswersProgress.text = it
            }
            viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
                progressBar.setProgress(it, true)
            }
            viewModel.enoughCountORightAnswers.observe(viewLifecycleOwner) {
                textViewAnswersProgress.setTextColor(getColorByState(it))
            }
            viewModel.enoughPercentORightAnswers.observe(viewLifecycleOwner) {
                progressBar.progressTintList = ColorStateList.valueOf(getColorByState(it))
            }
            viewModel.minPercent.observe(viewLifecycleOwner) {
                progressBar.secondaryProgress = it
            }
            viewModel.gameResult.observe(viewLifecycleOwner) {
                launchGameFinishedFragment(it)
            }
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun getOption(options: ArrayList<Int>): String {
        val index = Random.nextInt(options.size)
        val result = options[index].toString()
        options.removeAt(index)
        return result
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }
}