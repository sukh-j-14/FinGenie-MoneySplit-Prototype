package com.fingenie.ui.expenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fingenie.databinding.ItemExpenseBinding
import com.fingenie.models.Expense
import java.text.SimpleDateFormat
import java.util.Locale

class ExpensesAdapter(
    private val onExpenseClick: (Expense) -> Unit
) : ListAdapter<Expense, ExpensesAdapter.ViewHolder>(ExpenseDiffCallback()) {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemExpenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onExpenseClick(getItem(position))
                }
            }
        }

        fun bind(expense: Expense) {
            binding.apply {
                textViewDescription.text = expense.description
                textViewAmount.text = String.format("$%.2f", expense.amount)
                textViewDate.text = dateFormat.format(expense.date)
                textViewPaidBy.text = "Paid by: ${expense.paidBy}"
            }
        }
    }

    private class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
} 