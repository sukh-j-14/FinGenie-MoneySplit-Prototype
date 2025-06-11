package com.fingenie.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fingenie.databinding.FragmentAddGroupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddGroupFragment : Fragment() {
    private var _binding: FragmentAddGroupBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddGroupViewModel
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddGroupViewModel::class.java]

        setupCreateGroupButton()
    }

    private fun setupCreateGroupButton() {
        binding.buttonCreateGroup.setOnClickListener {
            val name = binding.editTextGroupName.text.toString()
            val description = binding.editTextGroupDescription.text.toString()

            if (name.isNotEmpty()) {
                createGroup(name, description)
            } else {
                Toast.makeText(context, "Please enter a group name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createGroup(name: String, description: String) {
        val userId = auth.currentUser?.uid ?: return

        val group = com.fingenie.models.Group(
            name = name,
            description = description,
            createdBy = userId,
            members = listOf(userId)
        )

        db.collection("groups")
            .add(group)
            .addOnSuccessListener {
                findNavController().navigateUp()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to create group: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 