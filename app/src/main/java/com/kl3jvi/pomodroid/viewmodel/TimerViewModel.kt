package com.kl3jvi.pomodroid.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

class TimerViewModel(application: Application) : AndroidViewModel(application) {
    var COUNTDOWN_LIMIT = 0L
    val ONE_SECOND = 1000L
    lateinit var timer: CountDownTimer
    var timerFinished = MutableLiveData<Boolean>()
    val currentTime = MutableLiveData<Long>()


    private fun setupTime(key: String): Long {
        COUNTDOWN_LIMIT = getTime(key)
        return COUNTDOWN_LIMIT
    }

    private fun timerInit(countDownTimer: Long) {
        timerFinished.value = false
        timer = object : CountDownTimer(setupTime("focus"), ONE_SECOND) {
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

    private fun getTime(key: String): Long {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(getApplication())
        return (sharedPreferences.getInt(key, 0) * 60_000).toLong()
    }
}
