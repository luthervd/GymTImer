package com.example.gymtimer.model

import androidx.compose.material.Text
import java.util.UUID

class Timer(val time: Long, val name: String) {
    val id : String = UUID.randomUUID().toString()

    fun format(): String{
        val minutes = time / 1000 / 60
        val seconds = time / 1000 % 60
        return "$minutes:$seconds"
    }
}
