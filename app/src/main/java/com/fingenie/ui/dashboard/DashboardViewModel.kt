package com.fingenie.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fingenie.models.Expense
import com.fingenie.models.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _groups = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>> = _groups

    private val _totalOwed = MutableLiveData<Double>()
    val totalOwed: LiveData<Double> = _totalOwed

    private val _totalOwedToYou = MutableLiveData<Double>()
    val totalOwedToYou: LiveData<Double> = _totalOwedToYou

    fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return

        // Load user's groups
        db.collection("groups")
            .whereArrayContains("members", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val groupsList = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Group::class.java)?.copy(id = doc.id)
                } ?: emptyList()

                _groups.value = groupsList
            }

        // Load expense summary
        db.collection("users")
            .document(userId)
            .collection("expense_summary")
            .document("summary")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val totalOwed = snapshot?.getDouble("total_owed") ?: 0.0
                val totalOwedToYou = snapshot?.getDouble("total_to_receive") ?: 0.0

                _totalOwed.value = totalOwed
                _totalOwedToYou.value = totalOwedToYou
            }
    }
} 