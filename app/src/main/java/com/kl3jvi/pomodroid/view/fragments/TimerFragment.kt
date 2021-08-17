package com.kl3jvi.pomodroid.view.fragments

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.kl3jvi.pomodroid.databinding.FragmentTimerBinding
import me.zhanghai.android.materialplaypausedrawable.MaterialPlayPauseDrawable


class TimerFragment : Fragment() {

    private var mBinding: FragmentTimerBinding? = null

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
        chronometer.base = SystemClock.elapsedRealtime() + transformTime("focus")
        chronometer.isCountDown = true



        mBinding!!.playPause.setOnClickListener {
            startStop()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun transformTime(key: String): Int {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        return sharedPreferences.getInt(key, 0) * 60000
    }

    private fun startStop() {


        val newState =
            if (mBinding!!.playPause.state === MaterialPlayPauseDrawable.State.Play)
                MaterialPlayPauseDrawable.State.Pause else MaterialPlayPauseDrawable.State.Play

        mBinding!!.playPause.state = newState


        when (newState) {
            MaterialPlayPauseDrawable.State.Play -> {
                mBinding!!.time.stop()
            }
            MaterialPlayPauseDrawable.State.Pause -> {
                mBinding!!.time.start()
                val max = transformTime("focus") / 1000F
                mBinding!!.time.setOnChronometerTickListener {
                    val elapsedMillis: Long = SystemClock.elapsedRealtime() - it.base
                    mBinding!!.circularProgressBar.progressMax = max
                    mBinding!!.circularProgressBar.setProgressWithAnimation(
                        ((max - elapsedMillis) / 1000),
                        1000
                    )
                }
            }
        }
    }
}
