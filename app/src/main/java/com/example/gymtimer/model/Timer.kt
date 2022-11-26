package com.example.gymtimer.model

class Timer(val time: Long, val name: String) {

    fun format(): String{
        val minutes = time / 1000 / 60
        val seconds = time / 1000 % 60
        return "$minutes:$seconds"
    }
}
