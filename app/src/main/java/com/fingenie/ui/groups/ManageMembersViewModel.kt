package com.fingenie.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fingenie.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ManageMembersViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _currentMembers = MutableLiveData<List<User>>()
    val currentMembers: LiveData<List<User>> = _currentMembers

    private val _availableMembers = MutableLiveData<List<User>>()
    val availableMembers: LiveData<List<User>> = _availableMembers

    private val addedMembers = mutableSetOf<String>()
    private val removedMembers = mutableSetOf<String>()
    private var currentGroupMembers = listOf<String>()

    fun loadGroupMembers(groupId: String) {
        db.collection("groups")
            .document(groupId)
            .get()
            .addOnSuccessListener { document ->
                currentGroupMembers = document.get("members") as? List<String> ?: emptyList()
                loadCurrentMembers()
                loadAvailableMembers()
            }
    }

    private fun loadCurrentMembers() {
        if (currentGroupMembers.isEmpty()) {
            _currentMembers.value = emptyList()
            return
        }

        db.collection("users")
            .whereIn("id", currentGroupMembers)
            .get()
            .addOnSuccessListener { documents ->
                val members = documents.mapNotNull { it.toObject(User::class.java) }
                _currentMembers.value = members
            }
    }

    private fun loadAvailableMembers() {
        val currentUserId = auth.currentUser?.uid ?: return

        db.collection("users")
            .whereNotIn("id", currentGroupMembers + currentUserId)
            .get()
            .addOnSuccessListener { documents ->
                val members = documents.mapNotNull { it.toObject(User::class.java) }
                _availableMembers.value = members
            }
    }

    fun searchUsers(query: String) {
        if (query.isEmpty()) {
            loadAvailableMembers()
            return
        }

        val currentUserId = auth.currentUser?.uid ?: return

        db.collection("users")
            .whereNotIn("id", currentGroupMembers + currentUserId)
            .get()
            .addOnSuccessListener { documents ->
                val members = documents.mapNotNull { it.toObject(User::class.java) }
                    .filter { it.name.contains(query, ignoreCase = true) }
                _availableMembers.value = members
            }
    }

    fun addMember(userId: String) {
        if (currentGroupMembers.contains(userId)) return
        addedMembers.add(userId)
        removedMembers.remove(userId)
    }

    fun removeMember(userId: String) {
        if (!currentGroupMembers.contains(userId)) return
        removedMembers.add(userId)
        addedMembers.remove(userId)
    }

    fun getAddedMembers(): List<String> = addedMembers.toList()
    fun getRemovedMembers(): List<String> = removedMembers.toList()
} 