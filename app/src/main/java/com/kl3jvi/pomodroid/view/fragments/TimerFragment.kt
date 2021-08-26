package com.kl3jvi.pomodroid.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.preference.PreferenceManager
import com.kl3jvi.pomodroid.databinding.FragmentTimerBinding
import com.kl3jvi.pomodroid.viewmodel.TimerViewModel
import me.zhanghai.android.materialplaypausedrawable.MaterialPlayPauseDrawable


class TimerFragment : Fragment() {

    private var mBinding: FragmentTimerBinding? = null
    private var timeWhenStopped: Long = 0
    private lateinit var mTimerViewModel: TimerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("onCreateView", "initialised")
        mBinding = FragmentTimerBinding.inflate(inflater, container, false)
        val root: View = mBinding!!.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("onViewCreated", "initialised")
        // To save state after going to another fragment i initialize the view model by activity
        // so it gets destroyed when the activity dies
        mTimerViewModel =
            ViewModelProvider(activity as ViewModelStoreOwner).get(TimerViewModel::class.java)

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

        mBinding!!.circularProgressBar.progressMax = getTime("focus").toFloat()
        mTimerViewModel.currentTime.observe(viewLifecycleOwner) { time ->
            mBinding!!.time.text = String.format(
                "%02d:%02d",
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(time),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(time) -
                        java.util.concurrent.TimeUnit.MINUTES.toSeconds(
                            java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(
                                time
                            )
                        )
            )
            mBinding!!.circularProgressBar.setProgressWithAnimation(time.toFloat(), 1000)
        }

        mTimerViewModel.timerFinished.observe(viewLifecycleOwner) { paused ->
            if (paused) {
                mBinding!!.playPause.state = PLAY
            } else {
                mBinding!!.playPause.state = PAUSE
            }
        }


    }

    private fun getTime(key: String): Long {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireActivity())
        return (sharedPreferences.getInt(key, 0) * 60_000).toLong()
    }

    companion object {
        const val ANIMATION_DURATION: Long = 1000
        val PLAY = MaterialPlayPauseDrawable.State.Play
        val PAUSE = MaterialPlayPauseDrawable.State.Pause

    }


}


