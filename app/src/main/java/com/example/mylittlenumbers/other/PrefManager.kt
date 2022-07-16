package com.example.mylittlenumbers.other

import android.content.Context
import android.content.SharedPreferences

class PrefManager(private val context: Context) {
    private val sharPref = context.getSharedPreferences("MyLittleGame", 0)
    private val editor: SharedPreferences.Editor = sharPref.edit()
    private val score = "recordTotal"
    private val speed = "speedTotal"
    private val firstNumber = "firstNumber"


    fun saveScore(i:Int){
        editor.putInt(score,i)
        editor.apply()
    }
    fun readScore()=sharPref.getInt(score,0)

    fun saveSpeed(i:Int){
        editor.putInt(speed,i)
        editor.apply()
    }
    fun readSpeed()=sharPref.getInt(speed,5)

    fun saveFirstNum(i:Int){
        editor.putInt(firstNumber,i)
        editor.apply()
    }
    fun readFirstNum()=sharPref.getInt(firstNumber,0)
}