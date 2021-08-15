package com.kl3jvi.pomodroid.view.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kl3jvi.pomodroid.application.PomodoroApplication
import com.kl3jvi.pomodroid.databinding.FragmentTaskListBinding
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.view.adapters.TaskAdapter
import com.kl3jvi.pomodroid.viewmodel.TaskViewModel
import com.kl3jvi.pomodroid.viewmodel.TaskViewModelFactory
import com.maxkeppeler.sheets.info.InfoSheet
import java.util.*

class TaskListFragment : Fragment() {

    private lateinit var mBinding: FragmentTaskListBinding
    private lateinit var mTaskAdapter: TaskAdapter

    private val mTaskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((requireActivity().application as PomodoroApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTaskListBinding.inflate(inflater, container, false)
        val root: View = mBinding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvTaskList.layoutManager = GridLayoutManager(requireActivity(), 1)
        mTaskAdapter = TaskAdapter(this@TaskListFragment)
        mBinding.rvTaskList.adapter = mTaskAdapter

        mTaskViewModel.allTaskList.observe(viewLifecycleOwner) { tasks ->
            tasks.let {
                for (task in it) {
                    if (it.isNotEmpty()) {
                        mBinding.noTaskTv.visibility = View.GONE
                        mBinding.rvTaskList.visibility = View.VISIBLE
                        mTaskAdapter.tasksList(it)
                    } else {
                        mBinding.noTaskTv.visibility = View.VISIBLE
                        mBinding.rvTaskList.visibility = View.GONE
                    }
                }
            }
        }
    }

    fun getRandomColor(): ColorStateList {
        val color: Int = Color.argb(255, 0, 144, 193)
        return ColorStateList.valueOf(color)
    }

    fun deleteTask(task: Task) {
        InfoSheet().show(requireContext()) {
            title("Do you want to delete task?")
            content("Task will be permanently deleted from the database.")
            onNegative("No") {
                // Handle event
                dismiss()
            }
            onPositive("Remove") {
                mTaskViewModel.delete(task)
                Toast.makeText(requireContext(), "Task Deleted Sucessfully", Toast.LENGTH_SHORT).show()
            }
        }


    }
}