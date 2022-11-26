package com.example.gymtimer.model

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymtimer.R
import com.example.gymtimer.utils.Utility
import com.example.gymtimer.utils.Utility.formatTime

class TimerViewModel: ViewModel() {

    // private time state
    private var _timerSection: TimerSection? = null
    private var _timer: Timer? = null
    private var _timers: ArrayDeque<Timer> = ArrayDeque()
    private var countDownTimer : CountDownTimer? = null;
    private var _time : String = ""
    private var _playState : PlayState = PlayState.WAITING_FOR_TIMER_SECTION
    private var _lastTickTime : Long = 0
    private var _roundNumber: Int = 0
    private var _roundIndex: Int = 0
    private var _numberOfRounds: Int = 0

    // For the time view
    private var _viewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val _progress = MutableLiveData(1.00F)
    val progress: LiveData<Float>
        get() = _progress

    init {
        setViewState()
    }

    fun setTimerSection(timerSection: TimerSection?){
        if(timerSection != null)
        {
            _timerSection = timerSection;
            _timers = _timerSection!!.getTimers()
            _numberOfRounds = _timers.size/ timerSection.timersPerRound()
            _timer = _timers.removeFirst()
            _roundIndex = 0
            _roundNumber = 1
            _playState = PlayState.READY_TO_PLAY
            setViewState()
        }
    }

    private fun setViewState(){
        val next : Timer? = _timers.firstOrNull()
        val currentTime = toTimerView(_timer)
        val nextTime = toTimerView(next)
        if(_roundIndex == (_timerSection?.timersPerRound() ?: 2)){
            _roundNumber++
            _roundIndex = 0
        }
        var nextViewState = ViewState(currentTime,nextTime,_time,_playState,_roundNumber, _numberOfRounds)
        _viewState.value = nextViewState
    }

    private fun toTimerView(timer: Timer?): TimerView?{
        if(timer == null) return null;
        return TimerView(timer.time.formatTime(),timer.name);
    }

    fun startTimer(context: Context) {
        startTimer(_timer?.time ?: 0, context)
    }

    private fun checkNextTimer(context: Context){
        countDownTimer?.cancel()
        _playState = PlayState.PAUSED
        _timer = _timers.removeFirstOrNull()
        if(_timer != null){
            _roundIndex++
            startTimer(_timer?.time ?: 0, context)
        }
        else{
            _playState = PlayState.FINISHED
            setViewState()
        }
    }

    fun pauseTimer(){
        countDownTimer?.cancel()
        _playState = PlayState.PAUSED
        setViewState()
    }

    fun resume(context: Context){
        startTimer(_lastTickTime, context)
    }

    fun requeue(context: Context){
        if(_timerSection != null){
            setTimerSection(_timerSection)
            startTimer(context)
        }
    }

    private fun startTimer(time: Long, context: Context) {
        if(countDownTimer != null)
        {
            countDownTimer?.cancel()
        }
        _playState = PlayState.PLAYING
        var mp = MediaPlayer.create(context, R.raw.bell)
        countDownTimer = object : CountDownTimer(time.toLong(), 50) {

            override fun onTick(millisRemaining: Long) {
                _lastTickTime = millisRemaining
                if(millisRemaining <= 4000L && mp.isPlaying == false)
                {
                    mp.start()
                }
                val progressValue = millisRemaining.toFloat() / (_timer?.time ?: 1000L)
                handleTimerValues(PlayState.PLAYING, (millisRemaining+1000).formatTime(), progressValue)
            }

            override fun onFinish() {

                checkNextTimer(context)
            }
        }.start()
    }

    private fun handleTimerValues(playState: PlayState, text: String, progress: Float) {
        _playState = playState
        _time = text
        _progress.value = progress
        setViewState()
    }
}


