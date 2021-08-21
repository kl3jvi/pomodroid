package com.kl3jvi.pomodroid.view.fragments

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.kl3jvi.pomodroid.databinding.FragmentTimerBinding
import com.kl3jvi.pomodroid.viewmodel.TimerViewModel
import com.kl3jvi.pomodroid.viewmodel.TimerViewModelFactory
import me.zhanghai.android.materialplaypausedrawable.MaterialPlayPauseDrawable


class TimerFragment : Fragment() {

    private var mBinding: FragmentTimerBinding? = null
    private var timeWhenStopped: Long = 0

    private val mTimerViewModel: TimerViewModel by viewModels {
        TimerViewModelFactory(mBinding!!.time)
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
        chronometer.base = getTime("focus")

        mBinding!!.playPause.setOnClickListener {
            val newState =
                if (mBinding!!.playPause.state === MaterialPlayPauseDrawable.State.Play)
                    MaterialPlayPauseDrawable.State.Pause else MaterialPlayPauseDrawable.State.Play
            mBinding!!.playPause.state = newState

            when (newState) {
                PLAY -> {
                    mTimerViewModel.play()
                }
                PAUSE -> {
                    mTimerViewModel.pause()
                }
                else -> {
                    Log.e("Error", "Something went wrong with play/pause")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
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


