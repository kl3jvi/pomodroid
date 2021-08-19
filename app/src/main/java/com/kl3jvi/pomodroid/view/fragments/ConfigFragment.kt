package com.kl3jvi.pomodroid.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.FragmentConfigBinding
import com.xw.repo.BubbleSeekBar
import com.xw.repo.BubbleSeekBar.OnProgressChangedListener


class ConfigFragment : Fragment(), OnProgressChangedListener {
    private var mBinding: FragmentConfigBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentConfigBinding.inflate(inflater, container, false)
        val root: View = mBinding!!.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding!!.seekBar1.onProgressChangedListener = this
        mBinding!!.seekBar2.onProgressChangedListener = this
        mBinding!!.seekBar3.onProgressChangedListener = this
        mBinding!!.seekBar4.onProgressChangedListener = this

        setupConfig()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onProgressChanged(
        bubbleSeekBar: BubbleSeekBar?,
        progress: Int,
        progressFloat: Float,
        fromUser: Boolean
    ) {
        Log.e(R.id.seekBar1.toString(), bubbleSeekBar!!.id.toString())
        when (bubbleSeekBar.id) {
            R.id.seekBar1 -> {
                mBinding!!.time1.text =
                    resources.getString(R.string.time_in_minutes, progress.toString())
                saveValue("focus", progress)
            }
            R.id.seekBar2 -> {
                mBinding!!.time2.text =
                    resources.getString(R.string.time_in_minutes, progress.toString())
                saveValue("break", progress)
            }
            R.id.seekBar3 -> {
                mBinding!!.time3.text =
                    resources.getString(R.string.time_in_minutes, progress.toString())
                saveValue("lBreak", progress)
            }
            R.id.seekBar4 -> {
                mBinding!!.time4.text =
                    resources.getString(R.string.rounds_format, progress.toString())
                saveValue("rounds", progress)
            }
        }


    }

    override fun getProgressOnActionUp(
        bubbleSeekBar: BubbleSeekBar?,
        progress: Int,
        progressFloat: Float
    ) {
    }

    override fun getProgressOnFinally(
        bubbleSeekBar: BubbleSeekBar?,
        progress: Int,
        progressFloat: Float,
        fromUser: Boolean
    ) {
    }

    private fun setupConfig() {
        mBinding!!.time1.text =
            resources.getString(R.string.time_in_minutes, loadValue("focus").toString())
        mBinding!!.seekBar1.setProgress(loadValue("focus").toFloat())
        mBinding!!.time2.text =
            resources.getString(R.string.time_in_minutes, loadValue("break").toString())
        mBinding!!.seekBar2.setProgress(loadValue("break").toFloat())
        mBinding!!.time3.text =
            resources.getString(R.string.time_in_minutes, loadValue("lBreak").toString())
        mBinding!!.seekBar3.setProgress(loadValue("lBreak").toFloat())
        mBinding!!.time4.text =
            resources.getString(R.string.time_in_minutes, loadValue("rounds").toString())
        mBinding!!.seekBar4.setProgress(loadValue("rounds").toFloat())
    }

    private fun saveValue(key: String?, value: Int) {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun loadValue(key: String): Int {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        return sharedPreferences.getInt(key, 0)
    }

}