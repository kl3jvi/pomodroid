package com.kl3jvi.pomodroid.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.FragmentTaskListBinding

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
}