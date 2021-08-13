package com.kl3jvi.pomodroid.view.fragments

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.application.PomodoroApplication
import com.kl3jvi.pomodroid.databinding.FragmentTaskListBinding
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.view.adapters.TaskAdapter
import com.kl3jvi.pomodroid.viewmodel.TaskViewModel
import com.kl3jvi.pomodroid.viewmodel.TaskViewModelFactory
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

        mBinding.rvTaskList.layoutManager = GridLayoutManager(requireActivity(), 2)
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
        val color: Int = Color.argb(255, 97, 146, 255)
        return ColorStateList.valueOf(color)
    }

    fun deleteTask(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Dish")
        builder.setMessage("Are you sure you want to delete this dish?")
        builder.setIcon(R.drawable.ic_baseline_warning_24)
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            mTaskViewModel.delete(task)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("NO") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}