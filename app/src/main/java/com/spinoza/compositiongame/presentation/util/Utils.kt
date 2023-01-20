package com.spinoza.compositiongame.presentation

fun calculatePercent(value: Int, maxValue: Int): Int {
    return if (maxValue == 0) {
        0
    } else {
        (value * 100.0 / maxValue).toInt()
    }
}