package com.example.mylittlenumbers.ui

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.mylittlenumbers.R
import com.example.mylittlenumbers.databinding.ActivityLearnNumbersBinding
import com.example.mylittlenumbers.other.invisible
import com.example.mylittlenumbers.other.visible

class LearnNumbers : AppCompatActivity() {
    private lateinit var binding:ActivityLearnNumbersBinding
    private var mediaPlayer: MediaPlayer? = null
    private var countDownTimer:CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLearnNumbersBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ln1.setOnClickListener {
            bigImage(R.drawable.images_1,R.string.one)
            playSoundNmber(R.raw.one)
        }
        binding.ln2.setOnClickListener {
            bigImage(R.drawable.images_2,R.string.two)
            playSoundNmber(R.raw.two)
        }
        binding.ln3.setOnClickListener {
            bigImage(R.drawable.images_3,R.string.three)
            playSoundNmber(R.raw.three)
        }
        binding.ln4.setOnClickListener {
            bigImage(R.drawable.images_4,R.string.four)
            playSoundNmber(R.raw.four)
        }
        binding.ln5.setOnClickListener {
            bigImage(R.drawable.images_5,R.string.five)
            playSoundNmber(R.raw.five)
        }
        binding.ln6.setOnClickListener {
            bigImage(R.drawable.images_6,R.string.six)
            playSoundNmber(R.raw.six)
        }
        binding.ln7.setOnClickListener {
            bigImage(R.drawable.images_7,R.string.seven)
            playSoundNmber(R.raw.seven)
        }
        binding.ln8.setOnClickListener {
            bigImage(R.drawable.images_8,R.string.eight)
            playSoundNmber(R.raw.eight)
        }
        binding.ln9.setOnClickListener {
            bigImage(R.drawable.images_9,R.string.nine)
            playSoundNmber(R.raw.nine)
        }
        binding.ln10.setOnClickListener {
            bigImage(R.drawable.images_10,R.string.ten)
            playSoundNmber(R.raw.ten)
        }

    }
    private fun playSoundNmber(int: Int){
        if (mediaPlayer!=null){
            mediaPlayer?.release()
        }
        countDownTimer?.cancel()
        mediaPlayer= MediaPlayer.create(this,int)
        mediaPlayer?.start()
       countDownTimer =
           object :CountDownTimer(2000,1000){
           override fun onTick(p0: Long) {}

           override fun onFinish() {
//               mediaPlayer?.stop()
               mediaPlayer?.release()
               binding.lnBigImgContainer.invisible()
               binding.lnNameNumber.invisible()
           }

       }.start()
    }
    private fun bigImage(int: Int,text:Int){
        binding.lnBigImgContainer.visible()
        binding.lnNameNumber.visible()
        binding.lnBigImg.setImageResource(int)
        binding.lnNameNumber.text = getString(text)
    }
}