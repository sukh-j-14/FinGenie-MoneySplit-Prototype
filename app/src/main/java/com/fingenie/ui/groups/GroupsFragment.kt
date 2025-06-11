package com.fingenie.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fingenie.R
import com.fingenie.databinding.FragmentGroupsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GroupsFragment : Fragment() {
    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GroupsViewModel by viewModels()
    private lateinit var adapter: GroupsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = GroupsAdapter { group ->
            findNavController().navigate(
                GroupsFragmentDirections.actionGroupsFragmentToManageMembersFragment(group.id)
            )
        }
        binding.recyclerGroups.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.groups.observe(viewLifecycleOwner) { groups ->
            adapter.submitList(groups)
        }
    }

    private fun setupClickListeners() {
        binding.fabAddGroup.setOnClickListener {
            findNavController().navigate(
                GroupsFragmentDirections.actionGroupsFragmentToAddGroupFragment()
            )
        }
    }

    private fun showDeleteConfirmationDialog(groupId: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_group_confirmation)
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteGroup(groupId)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 