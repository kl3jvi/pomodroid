package com.kl3jvi.pomodroid.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.FragmentTaskListBinding
import com.kl3jvi.pomodroid.view.activities.AddUpdateToDoList

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val root: View = _binding!!.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_task, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_task -> {
                startActivity(Intent(requireActivity(), AddUpdateToDoList::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}