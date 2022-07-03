package com.example.mylittlenumbers.other

import android.content.Context
import android.content.SharedPreferences

class PrefManager(private val context: Context) {
    private val sharPref = context.getSharedPreferences("MyLittleGame", 0)
    private val editor: SharedPreferences.Editor = sharPref.edit()
    private val score = "recordTotal"
    private val speed = "speedTotal"

    fun saveScore(i:Int){
        editor.putInt(score,i)
    }
    fun readScore()=sharPref.getInt(score,0)

    fun saveSpeed(i:Int){
        editor.putInt(speed,i)
    }
    fun readSpeed()=sharPref.getInt(speed,5)
}