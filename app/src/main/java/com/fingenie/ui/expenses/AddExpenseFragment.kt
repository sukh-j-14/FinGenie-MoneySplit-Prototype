package com.fingenie.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fingenie.R
import com.fingenie.databinding.FragmentAddExpenseBinding
import com.fingenie.models.Expense
import com.fingenie.models.ExpenseSplit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddExpenseFragment : Fragment() {
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddExpenseViewModel
    private lateinit var memberAdapter: MemberSelectionAdapter
    private val args: AddExpenseFragmentArgs by navArgs()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddExpenseViewModel::class.java]

        setupMemberAdapter()
        setupGroupMembers()
        setupAddExpenseButton()
    }

    private fun setupMemberAdapter() {
        memberAdapter = MemberSelectionAdapter { memberId, isSelected ->
            if (isSelected) {
                viewModel.toggleMemberSelection(memberId)
            } else {
                viewModel.toggleMemberSelection(memberId)
            }
        }

        binding.recyclerViewMembers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = memberAdapter
        }
    }

    private fun setupGroupMembers() {
        viewModel.loadGroupMembers(args.groupId)
        viewModel.groupMembers.observe(viewLifecycleOwner) { members ->
            memberAdapter.submitList(members)
        }
    }

    private fun setupAddExpenseButton() {
        binding.buttonAddExpense.setOnClickListener {
            val amount = binding.editTextAmount.text.toString().toDoubleOrNull()
            val description = binding.editTextDescription.text.toString()

            if (amount != null && description.isNotEmpty()) {
                createExpense(amount, description)
            } else {
                Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createExpense(amount: Double, description: String) {
        val userId = auth.currentUser?.uid ?: return
        val selectedMembers = viewModel.getSelectedMembers()

        if (selectedMembers.isEmpty()) {
            Toast.makeText(context, "Please select at least one member", Toast.LENGTH_SHORT).show()
            return
        }

        val splitAmount = amount / selectedMembers.size
        val splits = selectedMembers.map { memberId ->
            ExpenseSplit(
                userId = memberId,
                amount = splitAmount,
                isPaid = memberId == userId
            )
        }

        val expense = Expense(
            groupId = args.groupId,
            paidBy = userId,
            amount = amount,
            description = description,
            splits = splits
        )

        db.collection("expenses")
            .add(expense)
            .addOnSuccessListener { documentReference ->
                // Update group's expenses list
                db.collection("groups")
                    .document(args.groupId)
                    .update("expenses", com.google.firebase.firestore.FieldValue.arrayUnion(documentReference.id))
                    .addOnSuccessListener {
                        findNavController().navigateUp()
                    }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 