package com.example.mylittlenumbers.ui.guess_number

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

    private var speedTimer: CountDownTimer? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessNumberBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this)[GuessNumberVM::class.java]
        setContentView(binding.root)
        prefManager = PrefManager(this)

        vm.speed.value = prefManager.readSpeed()

        lifecycleScope.launchWhenCreated {
            vm.speed.collect{
                finishGame()
                binding.gnInterval.text = it.toString()
                prefManager.saveSpeed(it)
            }
        }
        binding.gnPlay.setOnClickListener {
            binding.gnPlay.invisible()
            startOneTimer()
            vm.isPlay = true
            vm.oldScore=prefManager.readScore()
            Timber.d("play on")
        }
        binding.gnStop.setOnClickListener {
            finishGame()
            vm.isPlay = false
            Timber.d("play off")
        }

        lifecycleScope.launchWhenCreated {
            vm.live.collect {
                if (it<0){
                    finishGame()
                }
                binding.gnLiveNum.text = it.toString()
                Timber.d("life is $it")
            }
        }

        lifecycleScope.launchWhenCreated {
            vm.wrongAnswer.collect {
                Timber.d("wrongNum changed $it")
                if (it>0){
                    Timber.d("success ")
                    speedTimer?.cancel()
                    playSuccess()

                }
                binding.gnRecordNum.text = it.toString()
            }
        }

        binding.gnPlus.setOnClickListener {
            vm.speed.value = vm.speed.value +1
        }

        binding.gnMinus.setOnClickListener {
            vm.speed.value = vm.speed.value -1
        }

        binding.gn1.setOnClickListener {
            if (vm.isPlay) {
                chooseWrite(1)
                bigImage(R.drawable.images_1)
                Timber.d("click 1")
            }
        }
        binding.gn2.setOnClickListener {
            chooseWrite(2)
            Timber.d("click 2")
        }
        binding.gn3.setOnClickListener {
            chooseWrite(3)
        }
        binding.gn4.setOnClickListener {
            chooseWrite(4)
        }
        binding.gn5.setOnClickListener {
            chooseWrite(5)
        }
        binding.gn6.setOnClickListener {
            chooseWrite(6)
        }
        binding.gn7.setOnClickListener {
            chooseWrite(7)
        }
        binding.gn8.setOnClickListener {
            chooseWrite(8)
        }
        binding.gn9.setOnClickListener {
            chooseWrite(9)
        }
        binding.gn10.setOnClickListener {
            chooseWrite(10)
        }


    }

    private fun waitTwoSec() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                startOneTimer()
            }
        }.start()
    }

    private fun chooseWrite(i: Int){
        if (i==vm.randomInt){
            vm.wrongAnswer.value=vm.wrongAnswer.value+1
        }else{
            minusOneLife()
            Timber.d("livevalue -1 = ${vm.live.value}")
            playFail()
        }
    }

    private fun playFail() {
        Timber.d("play fail")
        val mps = MediaPlayer.create(this,R.raw.try_again)
        mps.start()
        waitTwoSec()
    }

    private fun playSuccess(){
        Timber.d("play success")
        val mps = MediaPlayer.create(this,R.raw.good)
        mps.start()
        waitTwoSec()
    }

    override fun onPause() {
        super.onPause()
        vm.isPlay = false
        finishGame()
    }
    private fun finishGame(){
        vm.isPlay=false
        binding.gnPlay.visible()
        binding.gnBigImgContainer.invisible()
        speedTimer?.cancel()
        vm.live.value =3
        saveScore()
        vm.wrongAnswer.value = 0
        Timber.d("game stop")
        //TOdo ты побил рекорд
    }
    private fun startOneTimer(){
        Timber.d("startOneTimer")
        vm.playOne(this)
        speedTimer = object : CountDownTimer( (vm.speed.value*1000).toLong(),1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
            Timber.d("finish timer")
                minusOneLife()
            }

        }.start()
    }

    private fun minusOneLife() {
        vm.live.value = vm.live.value - 1
        speedTimer?.cancel()
        startOneTimer()

    }


    private fun saveScore() {
        if (vm.wrongAnswer.value > prefManager.readScore()) {
            prefManager.saveScore(vm.wrongAnswer.value)
        }
    }



    private fun bigImage(int: Int) {
        binding.gnBigImgContainer.visible()
        binding.gnBigImg.setImageResource(int)
    }
}