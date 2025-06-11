package com.fingenie.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fingenie.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class ProfileViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> = _updateStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    handleFirestoreError(e)
                    return@addSnapshotListener
                }

                snapshot?.toObject(User::class.java)?.let { user ->
                    _userProfile.value = user.copy(id = snapshot.id)
                } ?: run {
                    _error.value = "User profile not found"
                }
            }
    }

    fun updateProfile(name: String, email: String) {
        val userId = auth.currentUser?.uid ?: return

        val updates = mapOf(
            "name" to name,
            "email" to email
        )

        db.collection("users")
            .document(userId)
            .update(updates)
            .addOnSuccessListener {
                _updateStatus.value = true
            }
            .addOnFailureListener { e ->
                handleFirestoreError(e)
                _updateStatus.value = false
            }
    }

    fun updateProfilePicture(url: String) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .update("profilePictureUrl", url)
            .addOnSuccessListener {
                _updateStatus.value = true
            }
            .addOnFailureListener { e ->
                handleFirestoreError(e)
                _updateStatus.value = false
            }
    }

    private fun handleFirestoreError(e: Exception) {
        when (e) {
            is FirebaseFirestoreException -> {
                when (e.code) {
                    FirebaseFirestoreException.Code.PERMISSION_DENIED -> {
                        _error.value = "Permission denied. Please check your internet connection and try again."
                    }
                    FirebaseFirestoreException.Code.UNAVAILABLE -> {
                        _error.value = "Service unavailable. Please check your internet connection and try again."
                    }
                    else -> {
                        _error.value = "An error occurred: ${e.message}"
                    }
                }
            }
            else -> {
                _error.value = "An unexpected error occurred: ${e.message}"
            }
        }
    }
}