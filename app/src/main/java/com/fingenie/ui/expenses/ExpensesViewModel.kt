package com.fingenie.ui.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fingenie.models.Expense
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ExpensesViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    fun loadExpenses(groupId: String) {
        db.collection("expenses")
            .whereEqualTo("groupId", groupId)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val expensesList = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Expense::class.java)?.copy(id = doc.id)
                } ?: emptyList()

                _expenses.value = expensesList
            }
    }
} 