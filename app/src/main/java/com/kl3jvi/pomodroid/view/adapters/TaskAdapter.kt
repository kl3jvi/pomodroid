package com.kl3jvi.pomodroid.view.adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.ItemTaskBinding
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.utils.Constants
import com.kl3jvi.pomodroid.view.activities.AddUpdateToDoList
import com.kl3jvi.pomodroid.view.fragments.TaskListFragment

class TaskAdapter(private val fragment: Fragment) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private val mExpandedPosition: Int = -1
    private var tasks: List<Task> = listOf()

    class ViewHolder(view: ItemTaskBinding) : RecyclerView.ViewHolder(view.root) {
        val title = view.tvTitle
        val time = view.tvTime
        val chipGroup = view.chipGroup
        val content = view.tvContent
        val more = view.ibMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTaskBinding =
            ItemTaskBinding.inflate(
                LayoutInflater.from((fragment.context)), parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.time.text = task.timeStamp
        holder.content.text = task.content
        val arrayWithCategories = task.category.split(" ")
        arrayWithCategories.forEach {
            if (it.isNotEmpty()) {
                if (fragment is TaskListFragment) {
                    val chip = Chip(fragment.requireContext())
                    chip.text = it
                    chip.chipBackgroundColor = fragment.getRandomColor()
                    chip.setTextColor(Color.WHITE)
                    holder.chipGroup.addView(chip)
                }
            }
        }
        holder.more.setOnClickListener {
            val popupMenu = PopupMenu(fragment.context, holder.more)
            popupMenu.menuInflater.inflate(R.menu.menu_adapter, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit_task) {
                    val intent =
                        Intent(fragment.requireActivity(), AddUpdateToDoList::class.java)
                    intent.putExtra(Constants.EXTRA_TASK_DETAILS, task)
                    fragment.requireActivity().startActivity(intent)
                } else if (it.itemId == R.id.action_delete_task) {
                    if (fragment is TaskListFragment) {
                        fragment.deleteTask(task)
                    }
                }
                true
            }
            popupMenu.show()
        }

    }


    override fun getItemCount(): Int {
        return tasks.size
    }

    fun tasksList(list: List<Task>) {
        tasks = list
        notifyDataSetChanged()
    }
}