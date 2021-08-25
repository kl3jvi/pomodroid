package com.kl3jvi.pomodroid.view.fragments

import android.icu.util.TimeUnit
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.kl3jvi.pomodroid.application.PomodoroApplication
import com.kl3jvi.pomodroid.databinding.FragmentTimerBinding
import com.kl3jvi.pomodroid.viewmodel.TimerViewModel
import com.kl3jvi.pomodroid.viewmodel.TimerViewModelFactory
import me.zhanghai.android.materialplaypausedrawable.MaterialPlayPauseDrawable


class TimerFragment : Fragment() {

    private var mBinding: FragmentTimerBinding? = null
    private var timeWhenStopped: Long = 0

    private val mTimerViewModel: TimerViewModel by viewModels {
        TimerViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentTimerBinding.inflate(inflater, container, false)
        val root: View = mBinding!!.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chronometer = mBinding!!.time

        mBinding!!.playPause.setOnClickListener {
            val newState =
                if (mBinding!!.playPause.state === PLAY)
                    PAUSE else PLAY
            mBinding!!.playPause.state = newState

            when (newState) {
                PLAY -> {
                    mTimerViewModel.stop()
                }
                PAUSE -> {
                    mTimerViewModel.start()
                }
                else -> {
                    Log.e("Error", "Something went wrong with play/pause")
                }
            }
        }

        mTimerViewModel.currentTime.observe(viewLifecycleOwner) { time ->
            Log.e("Time", time.toString())
            chronometer.base = SystemClock.elapsedRealtime() + time
            mTimerViewModel.timerFinished.observe(viewLifecycleOwner) { paused ->
                if (paused) {
                    chronometer.start()
                } else {
                    chronometer.stop()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding!!.time.stop()
        mBinding = null
    }

    private fun getTime(key: String): Long {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        return (sharedPreferences.getInt(key, 0) * 60_000).toLong()
    }


    companion object {
        const val ANIMATION_DURATION: Long = 1000
        val PLAY = MaterialPlayPauseDrawable.State.Play
        val PAUSE = MaterialPlayPauseDrawable.State.Pause

    }


}


