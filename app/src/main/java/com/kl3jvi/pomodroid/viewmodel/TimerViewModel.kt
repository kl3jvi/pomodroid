package com.kl3jvi.pomodroid.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TimerViewModel() : ViewModel() {
    var COUNTDOWN_LIMIT = 30_000L
    val ONE_SECOND = 1000L
    lateinit var timer: CountDownTimer
    var timerFinished = MutableLiveData<Boolean>()
    val currentTime = MutableLiveData<Long>()

    init {
        timerFinished.value = false
    }

    private fun timerInit(countDownTimer: Long) {
        timer = object : CountDownTimer(countDownTimer, ONE_SECOND) {
            override fun onTick(timeLeft: Long) {
                currentTime.value = timeLeft
                COUNTDOWN_LIMIT = timeLeft
            }

            override fun onFinish() {
                timerFinished.value = true
            }
        }
    }

    fun start() {
        timerInit(COUNTDOWN_LIMIT)
        timer.start()
    }

    fun stop() {
        timer.cancel()
    }
}

class TimerViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}