package com.fingenie.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fingenie.databinding.FragmentDashboardBinding
import com.fingenie.ui.groups.GroupsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DashboardViewModel
    private lateinit var groupsAdapter: GroupsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        setupGroupsAdapter()
        setupFab()
        setupProfileButton()
        loadData()
    }

    private fun setupGroupsAdapter() {
        groupsAdapter = GroupsAdapter { group ->
            val action = DashboardFragmentDirections
                .actionDashboardFragmentToExpensesFragment(group.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewGroups.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupsAdapter
        }
    }

    private fun setupFab() {
        binding.fabAddGroup.setOnClickListener {
            val action = DashboardFragmentDirections
                .actionDashboardFragmentToGroupsFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupProfileButton() {
        binding.buttonProfile.apply {
            isClickable = true
            isFocusable = true
            setOnClickListener {
                Log.d("DashboardFragment", "Profile button clicked")
                try {
                    val action = DashboardFragmentDirections
                        .actionDashboardFragmentToProfileFragment()
                    findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.e("DashboardFragment", "Navigation error", e)
                }
            }
        }
    }

    private fun loadData() {
        viewModel.loadUserData()
        viewModel.groups.observe(viewLifecycleOwner) { groups ->
            groupsAdapter.submitList(groups)
        }

        viewModel.totalOwed.observe(viewLifecycleOwner) { amount ->
            binding.textViewTotalOwed.text = String.format("$%.2f", amount)
        }

        viewModel.totalOwedToYou.observe(viewLifecycleOwner) { amount ->
            binding.textViewTotalOwedToYou.text = String.format("$%.2f", amount)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 