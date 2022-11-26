package com.example.gymtimer.model

data class ViewState(val current: TimerView?,
                     val next: TimerView?,
                     val time: String,
                     val playState: PlayState,
                     val roundNumber: Int,
                     val numberOfRounds: Int) {

    fun hasCurrent(): Boolean {
        return current != null
    }

    fun hasNext(): Boolean {
        return next != null
    }

    var currentName = current?.name ?: ""
}