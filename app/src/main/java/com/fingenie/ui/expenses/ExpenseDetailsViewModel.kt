package com.fingenie.ui.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fingenie.models.Expense
import com.google.firebase.firestore.FirebaseFirestore

class ExpenseDetailsViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _expense = MutableLiveData<Expense>()
    val expense: LiveData<Expense> = _expense

    fun loadExpenseDetails(expenseId: String) {
        db.collection("expenses")
            .document(expenseId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshot?.toObject(Expense::class.java)?.let { expense ->
                    _expense.value = expense.copy(id = snapshot.id)
                }
            }
    }
} 