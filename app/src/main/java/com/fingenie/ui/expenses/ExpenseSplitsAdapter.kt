package com.fingenie.ui.expenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fingenie.databinding.ItemExpenseSplitBinding
import com.fingenie.models.ExpenseSplit
import com.google.firebase.firestore.FirebaseFirestore

class ExpenseSplitsAdapter : ListAdapter<ExpenseSplit, ExpenseSplitsAdapter.ViewHolder>(SplitDiffCallback()) {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExpenseSplitBinding.inflate(
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
        private val binding: ItemExpenseSplitBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(split: ExpenseSplit) {
            // Load user details
            db.collection("users")
                .document(split.userId)
                .get()
                .addOnSuccessListener { document ->
                    val userName = document.getString("name") ?: "Unknown User"
                    binding.textViewUserName.text = userName
                }

            binding.textViewAmount.text = String.format("$%.2f", split.amount)
            binding.checkBoxPaid.isChecked = split.isPaid
        }
    }

    private class SplitDiffCallback : DiffUtil.ItemCallback<ExpenseSplit>() {
        override fun areItemsTheSame(oldItem: ExpenseSplit, newItem: ExpenseSplit): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: ExpenseSplit, newItem: ExpenseSplit): Boolean {
            return oldItem == newItem
        }
    }
}