package com.example.mylittlenumbers.ui.guess_number

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.example.mylittlenumbers.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import kotlin.random.Random

class GuessNumberVM : ViewModel() {
    private var mp: MediaPlayer? = null
    val wrongAnswer = MutableStateFlow(0)
    var randomInt = 0
    var oldScore = 0
    var firstNumber = 0
    var firstStart = true

    val speed = MutableStateFlow(5)

    val live = MutableStateFlow(3)

    var isPlay = false

    fun playOne(context: Context) {
        if (isPlay) {
            randomInt = Random.nextInt(1, 11)
            if (firstStart) {
                while (randomInt == firstNumber) {
                    randomInt = Random.nextInt(1, 11)
                }
            }
            firstStart=false
            when (randomInt) {
                1 -> {
                    createMP(R.raw.one, context)
                }
                2 -> {
                    createMP(R.raw.two, context)
                }
                3 -> {
                    createMP(R.raw.three, context)
                }
                4 -> {
                    createMP(R.raw.four, context)
                }
                5 -> {
                    createMP(R.raw.five, context)
                }
                6 -> {
                    createMP(R.raw.six, context)
                }
                7 -> {
                    createMP(R.raw.seven, context)
                }
                8 -> {
                    createMP(R.raw.eight, context)
                }
                9 -> {
                    createMP(R.raw.nine, context)
                }
                10 -> {
                    createMP(R.raw.ten, context)
                }

            }
        }
    }

    private fun createMP(i: Int, context: Context) {
        mp = MediaPlayer.create(context, i)
        mp?.start()
        Timber.d("rnd num $randomInt")
    }
}