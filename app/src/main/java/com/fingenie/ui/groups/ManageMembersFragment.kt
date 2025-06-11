package com.fingenie.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fingenie.databinding.FragmentManageMembersBinding
import com.fingenie.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ManageMembersFragment : Fragment() {
    private var _binding: FragmentManageMembersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ManageMembersViewModel
    private lateinit var currentMembersAdapter: CurrentMembersAdapter
    private lateinit var addMembersAdapter: AddMembersAdapter
    private val args: ManageMembersFragmentArgs by navArgs()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ManageMembersViewModel::class.java]

        setupAdapters()
        setupSearch()
        setupSaveButton()
        loadMembers()
    }

    private fun setupAdapters() {
        currentMembersAdapter = CurrentMembersAdapter { userId ->
            viewModel.removeMember(userId)
        }

        addMembersAdapter = AddMembersAdapter { userId ->
            viewModel.addMember(userId)
        }

        binding.recyclerViewCurrentMembers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currentMembersAdapter
        }

        binding.recyclerViewAddMembers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = addMembersAdapter
        }
    }

    private fun setupSearch() {
        binding.editTextSearch.addTextChangedListener { text ->
            viewModel.searchUsers(text?.toString() ?: "")
        }
    }

    private fun setupSaveButton() {
        binding.fabSave.setOnClickListener {
            saveChanges()
        }
    }

    private fun loadMembers() {
        viewModel.loadGroupMembers(args.groupId)
        viewModel.currentMembers.observe(viewLifecycleOwner) { members ->
            currentMembersAdapter.submitList(members)
        }

        viewModel.availableMembers.observe(viewLifecycleOwner) { members ->
            addMembersAdapter.submitList(members)
        }
    }

    private fun saveChanges() {
        val addedMembers = viewModel.getAddedMembers()
        val removedMembers = viewModel.getRemovedMembers()

        if (addedMembers.isEmpty() && removedMembers.isEmpty()) {
            findNavController().navigateUp()
            return
        }

        db.collection("groups")
            .document(args.groupId)
            .update(
                mapOf(
                    "members" to com.google.firebase.firestore.FieldValue.arrayUnion(*addedMembers.toTypedArray()),
                    "members" to com.google.firebase.firestore.FieldValue.arrayRemove(*removedMembers.toTypedArray())
                )
            )
            .addOnSuccessListener {
                Toast.makeText(context, "Members updated successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to update members: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 