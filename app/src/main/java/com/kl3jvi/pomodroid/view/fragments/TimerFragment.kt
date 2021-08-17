package com.kl3jvi.pomodroid.view.fragments

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kl3jvi.pomodroid.databinding.FragmentTimerBinding

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
        mBinding!!.time.base = SystemClock.elapsedRealtime() + 1_500_000
        mBinding!!.time.isCountDown = true
        mBinding!!.time.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}