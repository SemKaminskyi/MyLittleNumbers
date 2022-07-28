package com.example.mylittlenumbers.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mylittlenumbers.databinding.ActivityMainBinding
import com.example.mylittlenumbers.ui.game.LearnNumbers
import com.example.mylittlenumbers.ui.game.ariphmetic.AriphmeticActivity
import com.example.mylittlenumbers.ui.game.guess_number.GuessNumber
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mainGame1.setOnClickListener {
            Timber.d("game1Click")
            startActivity(Intent(this, LearnNumbers::class.java))
        }
        binding.mainGame2.setOnClickListener {
            startActivity(Intent(this,GuessNumber::class.java))
        }
        binding.mainGame3.setOnClickListener {
            startActivity(Intent(this,AriphmeticActivity::class.java))
        }
    }
}