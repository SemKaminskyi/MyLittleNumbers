package com.example.mylittlenumbers.ui.game.guess_number

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mylittlenumbers.R
import com.example.mylittlenumbers.databinding.ActivityGuessNumberBinding
import com.example.mylittlenumbers.other.PrefManager
import com.example.mylittlenumbers.other.gone
import com.example.mylittlenumbers.other.invisible
import com.example.mylittlenumbers.other.visible
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class GuessNumber : AppCompatActivity() {
    private lateinit var binding: ActivityGuessNumberBinding
    private lateinit var vm: GuessNumberVM


    private lateinit var prefManager: PrefManager

    private var speedTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessNumberBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this)[GuessNumberVM::class.java]
        setContentView(binding.root)
        prefManager = PrefManager(this)

        vm.speed.value = prefManager.readSpeed()
        vm.firstNumber = prefManager.readFirstNum()

        lifecycleScope.launchWhenCreated {
            vm.speed.collect {
                binding.gnInterval.text = it.toString()
                prefManager.saveSpeed(it)
                if (vm.isPlay) {
                    finishGame()
                }
            }
        }

        binding.gnPlay.setOnClickListener {
            binding.gnPlay.invisible()
            vm.isPlay = true
            startOneTimer()
            vm.oldScore = prefManager.readScore()
            Timber.d("play on")
        }
        binding.gnStop.setOnClickListener {
            finishGame()
            Timber.d("play off")
        }

        lifecycleScope.launchWhenCreated {
            vm.live.collect {
                binding.gnLiveNum.text = it.toString()
                Timber.d("life is $it")
            }
        }

        lifecycleScope.launchWhenCreated {
            vm.wrongAnswer.collect {
                Timber.d("wrongNum changed $it")
                if (it > 0) {
                    Timber.d("success ")
                    speedTimer?.cancel()
                    playSuccess()

                }
                binding.gnRecordNum.text = it.toString()
            }
        }

        binding.gnPlus.setOnClickListener {
            vm.speed.value = vm.speed.value + 1
        }

        binding.gnMinus.setOnClickListener {
            vm.speed.value = vm.speed.value - 1
        }

        binding.gn1.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_1)
                chooseWrite(1)
                Timber.d("click 1")
            }
        }
        binding.gn2.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_2)
                chooseWrite(2)
            }
        }
        binding.gn3.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_3)
                chooseWrite(3)
            }
        }
        binding.gn4.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_4)
                chooseWrite(4)
            }
        }
        binding.gn5.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_5)
                chooseWrite(5)
            }
        }
        binding.gn6.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_6)
                chooseWrite(6)
            }
        }
        binding.gn7.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_7)
                chooseWrite(7)
            }
        }
        binding.gn8.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_8)
                chooseWrite(8)
            }
        }
        binding.gn9.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_9)
                chooseWrite(9)
            }
        }
        binding.gn10.setOnClickListener {
            if (vm.isPlay) {
                bigImage(R.drawable.images_10)
                chooseWrite(10)
            }
        }


    }

    private fun waitTwoSec(bol: Boolean,  int: Int) {
        object : CountDownTimer((int*1000).toLong(), 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                binding.gnBigImgContainer.invisible()
                if (bol) {
                    startOneTimer()
                } else {
                    finishGame()
                }
            }
        }.start()
    }

    private fun chooseWrite(i: Int) {
        speedTimer?.cancel()
        if (i == vm.randomInt) {
            vm.wrongAnswer.value = vm.wrongAnswer.value + 1
        } else {
            playFail()
            Timber.d("livevalue -1 = ${vm.live.value}")
        }
    }

    private fun playFail() {
        Timber.d("play fail")
        minusOneLife()
        //TODO  add diiferent sound try again
        val mps = MediaPlayer.create(this, R.raw.try_again)
        mps.start()
        if (vm.live.value == 0) {
            waitTwoSec(false,2)
        } else {
            waitTwoSec(true,2)
        }
    }

    private fun playFinish() {
        Timber.d("play finish")
        val mps = MediaPlayer.create(this, R.raw.finish_game)
        mps.start()
    }

    private fun playSuccess() {
        Timber.d("play success")
        val mps = MediaPlayer.create(this, R.raw.good)
        mps.start()
        waitTwoSec(true,2)
    }
    private fun playNewScore() {
        Timber.d("play new score")
        val mps = MediaPlayer.create(this, R.raw.new_score)
        mps.start()
        waitTwoSec(false,4)
    }

    override fun onPause() {
        super.onPause()
        if (vm.isPlay) finishGame()
    }

    private fun finishGame() {
        vm.isPlay = false
        binding.gnBigImgContainer.invisible()
        binding.gnPlay.visible()
        binding.gnNewRecordContainer.gone()
        speedTimer?.cancel()
        saveScore()
        prefManager.saveFirstNum(vm.firstNumber)
        vm.wrongAnswer.value = 0
        Timber.d("game stop")
        vm.live.value = 3
    }

    private fun startOneTimer() {
        if (vm.isPlay) {
            Timber.d("startOneTimer")
            vm.playOne(this)
            speedTimer = object : CountDownTimer((vm.speed.value * 1000).toLong(), 1000) {
                override fun onTick(p0: Long) {}

                override fun onFinish() {
                    Timber.d("finish timer")
                    playFail()
                }

            }.start()
        }
    }

    private fun minusOneLife() {
        vm.live.value = vm.live.value - 1
        speedTimer?.cancel()
    }


    private fun saveScore() {
        if (vm.wrongAnswer.value > prefManager.readScore()) {
            prefManager.saveScore(vm.wrongAnswer.value)
            binding.gnNewRecord.text = vm.wrongAnswer.value.toString()
            binding.gnNewRecordContainer.visible()
            vm.oldScore = vm.wrongAnswer.value
            playNewScore()
        }else{
            playFinish()
        }
    }


    private fun bigImage(int: Int) {
        binding.gnBigImg.setImageResource(int)
        binding.gnBigImgContainer.visible()
    }
}