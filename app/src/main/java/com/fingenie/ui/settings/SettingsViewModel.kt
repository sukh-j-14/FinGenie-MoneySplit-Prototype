package com.fingenie.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fingenie.models.Settings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings> = _settings

    fun loadSettings() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("settings")
            .document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    snapshot.toObject(Settings::class.java)?.let { settings ->
                        _settings.value = settings
                    }
                } else {
                    // Create default settings if none exist
                    val defaultSettings = Settings(
                        useLocalCurrency = true,
                        enableNotifications = true,
                        useDarkMode = false
                    )
                    db.collection("settings")
                        .document(userId)
                        .set(defaultSettings)
                    _settings.value = defaultSettings
                }
            }
    }

    fun setCurrencyPreference(useLocalCurrency: Boolean) {
        updateSettings("useLocalCurrency", useLocalCurrency)
    }

    fun setNotificationPreference(enableNotifications: Boolean) {
        updateSettings("enableNotifications", enableNotifications)
    }

    fun setDarkModePreference(useDarkMode: Boolean) {
        updateSettings("useDarkMode", useDarkMode)
    }

    private fun updateSettings(field: String, value: Boolean) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("settings")
            .document(userId)
            .update(field, value)
    }
} 