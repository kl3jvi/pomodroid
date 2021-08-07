package com.kl3jvi.pomodroid.view.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.application.PomodoroApplication
import com.kl3jvi.pomodroid.databinding.ActivityAddUpdateToDoListBinding
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.viewmodel.TaskViewModel
import com.kl3jvi.pomodroid.viewmodel.TaskViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AddUpdateToDoList : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityAddUpdateToDoListBinding
    private val mTaskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as PomodoroApplication).repository)
    }

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
                    var category: String = ""
                    for (chip in mBinding.categoryChip.allChips) {
                        val text = chip.text
                        category += "$text "
                    }
                    val description = mBinding.edContent.text.toString().trim { it <= ' ' }
                    val ts: Long = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
                    val date = Date(ts * 1000L)
                    val format: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val timeToSave = format.format(date)
                    when {
                        TextUtils.isEmpty(title) -> {
                            Toast.makeText(
                                this,
                                getString(R.string.err_msg_enter_title),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        TextUtils.isEmpty(category) -> {
                            Toast.makeText(
                                this,
                                getString(R.string.err_msg_enter_category),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        TextUtils.isEmpty(description) -> {
                            Toast.makeText(
                                this,
                                getString(R.string.err_msg_enter_description),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            val taskDetails = Task(
                                title,
                                timeToSave,
                                category,
                                description
                            )
                            mTaskViewModel.insert(taskDetails)
                            Toast.makeText(
                                this,
                                "You successfully added your task details.",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }
                R.id.bt_cancel -> {
                    finish()
                }
            }

        }
    }
}