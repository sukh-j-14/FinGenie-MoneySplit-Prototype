package com.fingenie.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fingenie.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        setupCurrencyPreference()
        setupNotificationPreference()
        setupThemePreference()
        loadSettings()
    }

    private fun setupCurrencyPreference() {
        binding.switchCurrency.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setCurrencyPreference(isChecked)
        }
    }

    private fun setupNotificationPreference() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationPreference(isChecked)
        }
    }

    private fun setupThemePreference() {
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkModePreference(isChecked)
        }
    }

    private fun loadSettings() {
        viewModel.loadSettings()
        viewModel.settings.observe(viewLifecycleOwner) { settings ->
            binding.switchCurrency.isChecked = settings.useLocalCurrency
            binding.switchNotifications.isChecked = settings.enableNotifications
            binding.switchDarkMode.isChecked = settings.useDarkMode
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 