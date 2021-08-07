package com.kl3jvi.pomodroid.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.ActivityAddUpdateToDoListBinding

class AddUpdateToDoList : AppCompatActivity() {
    private lateinit var mBinding : ActivityAddUpdateToDoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateToDoListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.title = getString(R.string.add_task)


    }
}