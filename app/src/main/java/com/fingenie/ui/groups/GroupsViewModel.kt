package com.fingenie.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fingenie.models.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GroupsViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _groups = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>> = _groups

    fun loadGroups() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("groups")
            .whereArrayContains("members", userId)
            .get()
            .addOnSuccessListener { documents ->
                val groups = documents.mapNotNull { doc ->
                    doc.toObject(Group::class.java).copy(id = doc.id)
                }
                _groups.value = groups
            }
    }

    fun addMemberToGroup(groupId: String, memberEmail: String) {
        db.collection("users")
            .whereEqualTo("email", memberEmail)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val memberId = documents.documents[0].id
                    db.collection("groups")
                        .document(groupId)
                        .update("members", com.google.firebase.firestore.FieldValue.arrayUnion(memberId))
                }
            }
    }

    fun removeMemberFromGroup(groupId: String, memberId: String) {
        db.collection("groups")
            .document(groupId)
            .update("members", com.google.firebase.firestore.FieldValue.arrayRemove(memberId))
    }

    fun deleteGroup(groupId: String) {
        db.collection("groups")
            .document(groupId)
            .delete()
            .addOnSuccessListener {
                loadGroups() // Reload the groups list after deletion
            }
    }
} 