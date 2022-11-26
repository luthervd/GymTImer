package com.example.gymtimer.model
import kotlin.collections.ArrayDeque

class RoundTimer(private var name: String) : TimerSection
{

    private var work: Timer? = null
    private var rest: Timer? = null
    private var numberOfRounds: Long = 0

    fun addWork(timer: Timer){
         work = timer
    }

    fun addRest(timer: Timer){
        rest = timer
    }

    fun addNumber(count: Long){
        numberOfRounds = count
    }

    override fun getTimers(): ArrayDeque<Timer> {
        if(work == null || rest == null){
            return ArrayDeque()
        }
        else{
            val result =  ArrayDeque<Timer>()
            var i = 0;
            while(i < numberOfRounds) {
                result.add(work!!)
                result.add(rest!!)
                i++
            }
            return result
        }
    }

    override fun timersPerRound(): Int {
        return 2
    }

    override fun getName(): String {
        return name
    }

}
