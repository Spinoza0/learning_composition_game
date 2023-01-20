package com.spinoza.compositiongame.presentation.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.spinoza.compositiongame.R
import com.spinoza.compositiongame.domain.entity.GameResult
import com.spinoza.compositiongame.presentation.calculatePercent

@BindingAdapter("requiredAnswers")
fun findRequiredAnswers(textView: TextView, requiredScore: Int) {
    textView.text =
        String.format(textView.context.getString(R.string.required_score), requiredScore)
}

@BindingAdapter("scoreAnswers")
fun findScoreAnswers(textView: TextView, scoreAnswers: Int) {
    textView.text = String.format(textView.context.getString(R.string.score_answers), scoreAnswers)
}

@BindingAdapter("requiredPercentage")
fun findRequiredPercentage(textView: TextView, requiredPercentage: Int) {
    textView.text =
        String.format(textView.context.getString(R.string.required_percentage), requiredPercentage)
}

@BindingAdapter("scorePercentage")
fun findScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(textView.context.getString(R.string.score_percentage),
        calculatePercent(gameResult.countOfRightAnswers, gameResult.countOfQuestions))
}

@BindingAdapter("emojiResult")
fun findEmojiResult(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    })
}