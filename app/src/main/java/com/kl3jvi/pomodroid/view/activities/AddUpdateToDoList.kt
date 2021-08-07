package com.kl3jvi.pomodroid.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.ActivityAddUpdateToDoListBinding

class AddUpdateToDoList : AppCompatActivity(), View.OnClickListener {
    private lateinit var mBinding: ActivityAddUpdateToDoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateToDoListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.title = getString(R.string.add_task)

        mBinding.btSave.setOnClickListener(this)
        mBinding.btCancel.setOnClickListener(this)

        mBinding.categoryChip.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        mBinding.categoryChip.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);


    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.bt_save -> {
                    val title = mBinding.edTitle.text.toString().trim { it <= ' ' }
                    Log.e("Titulli",title)
                }
            }

        }
    }
}