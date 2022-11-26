package com.example.gymtimer.utils

import java.util.concurrent.TimeUnit

object Utility{

    const val TIME_COUNTDOWN: Long = 60000L
    private const val TIME_FORMAT = "%02d:%02d"

    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )

    fun Int.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this.toLong()),
        TimeUnit.MILLISECONDS.toSeconds(this.toLong()) % 60
    )
}