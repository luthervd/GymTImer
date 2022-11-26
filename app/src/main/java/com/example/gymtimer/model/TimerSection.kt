package com.example.gymtimer.model

interface TimerSection{
    fun timersPerRound(): Int
    fun getName(): String
    fun getTimers(): ArrayDeque<Timer>
}