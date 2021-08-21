package com.kl3jvi.pomodroid.viewmodel

import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.zhanghai.android.materialplaypausedrawable.MaterialPlayPauseDrawable

class TimerViewModel(chronometer: Chronometer) : ViewModel() {
    private var timeWhenStopped: Long = 0
    private val chronometerInstance by lazy { chronometer }

    fun play() {
        timeWhenStopped = SystemClock.elapsedRealtime();
        chronometerInstance.stop()
    }

    fun pause() {
        chronometerInstance.base =
            chronometerInstance.base + SystemClock.elapsedRealtime() - timeWhenStopped
        chronometerInstance.start()
    }

    fun reset() {

    }


}

class TimerViewModelFactory(private val chrono: Chronometer) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(chrono) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}