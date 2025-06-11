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
import com.fingenie.databinding.FragmentExpenseDetailsBinding
import com.fingenie.models.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseDetailsFragment : Fragment() {
    private var _binding: FragmentExpenseDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ExpenseDetailsViewModel
    private lateinit var splitsAdapter: ExpenseSplitsAdapter
    private val args: ExpenseDetailsFragmentArgs by navArgs()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ExpenseDetailsViewModel::class.java]

        setupSplitsAdapter()
        setupSettleButton()
        loadExpenseDetails()
    }

    private fun setupSplitsAdapter() {
        splitsAdapter = ExpenseSplitsAdapter()
        binding.recyclerViewSplits.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = splitsAdapter
        }
    }

    private fun setupSettleButton() {
        binding.fabSettle.setOnClickListener {
            settleExpense()
        }
    }

    private fun loadExpenseDetails() {
        viewModel.loadExpenseDetails(args.expenseId)
        viewModel.expense.observe(viewLifecycleOwner) { expense ->
            updateUI(expense)
        }
    }

    private fun updateUI(expense: Expense) {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        
        binding.apply {
            textViewDescription.text = expense.description
            textViewAmount.text = String.format("$%.2f", expense.amount)
            textViewDate.text = dateFormat.format(expense.date)
            textViewPaidBy.text = getString(R.string.paid_by, expense.paidBy)
            
            splitsAdapter.submitList(expense.splits)
        }
    }

    private fun settleExpense() {
        val userId = auth.currentUser?.uid ?: return
        val expense = viewModel.expense.value ?: return

        // Find the user's split
        val userSplit = expense.splits.find { it.userId == userId } ?: return

        if (userSplit.isPaid) {
            Toast.makeText(context, "This expense is already settled", Toast.LENGTH_SHORT).show()
            return
        }

        // Update the split status
        db.collection("expenses")
            .document(expense.id)
            .update("splits", expense.splits.map { 
                if (it.userId == userId) it.copy(isPaid = true) else it 
            })
            .addOnSuccessListener {
                Toast.makeText(context, "Expense settled successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to settle expense: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 