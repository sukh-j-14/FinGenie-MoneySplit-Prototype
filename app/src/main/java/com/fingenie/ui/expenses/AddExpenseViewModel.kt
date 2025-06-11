package com.fingenie.ui.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fingenie.models.User
import com.google.firebase.firestore.FirebaseFirestore

class AddExpenseViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _groupMembers = MutableLiveData<List<User>>()
    val groupMembers: LiveData<List<User>> = _groupMembers

    private val selectedMembers = mutableSetOf<String>()

    fun loadGroupMembers(groupId: String) {
        db.collection("groups")
            .document(groupId)
            .get()
            .addOnSuccessListener { document ->
                val memberIds = document.get("members") as? List<String> ?: return@addOnSuccessListener

                db.collection("users")
                    .whereIn("id", memberIds)
                    .get()
                    .addOnSuccessListener { documents ->
                        val members = documents.mapNotNull { it.toObject(User::class.java) }
                        _groupMembers.value = members
                    }
            }
    }

    fun toggleMemberSelection(memberId: String) {
        if (selectedMembers.contains(memberId)) {
            selectedMembers.remove(memberId)
        } else {
            selectedMembers.add(memberId)
        }
    }

    fun getSelectedMembers(): List<String> = selectedMembers.toList()
} 