package com.fingenie.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fingenie.databinding.FragmentExpensesBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpensesFragment : Fragment() {
    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ExpensesViewModel
    private lateinit var expensesAdapter: ExpensesAdapter
    private val args: ExpensesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ExpensesViewModel::class.java]

        setupExpensesAdapter()
        setupFab()
        loadExpenses()
    }

    private fun setupExpensesAdapter() {
        expensesAdapter = ExpensesAdapter { expense ->
            val action = ExpensesFragmentDirections
                .actionExpensesFragmentToExpenseDetailsFragment(expense.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewExpenses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expensesAdapter
        }
    }

    private fun setupFab() {
        binding.fabAddExpense.setOnClickListener {
            val action = ExpensesFragmentDirections
                .actionExpensesFragmentToAddExpenseFragment(args.groupId)
            findNavController().navigate(action)
        }
    }

    private fun loadExpenses() {
        viewModel.loadExpenses(args.groupId)
        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            expensesAdapter.submitList(expenses)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 