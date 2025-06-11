package com.fingenie.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.fingenie.R
import com.fingenie.databinding.FragmentProfileBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uploadProfilePicture(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        if (!isGooglePlayServicesAvailable()) {
            showGooglePlayServicesError()
            return
        }
        
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        setupProfilePicture()
        setupSaveButton()
        setupLogoutButton()
        loadUserProfile()
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(requireContext())
        return resultCode == ConnectionResult.SUCCESS
    }

    private fun showGooglePlayServicesError() {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(requireContext())
        
        if (googleApiAvailability.isUserResolvableError(resultCode)) {
            googleApiAvailability.getErrorDialog(requireActivity(), resultCode, 2404)?.show()
        } else {
            Toast.makeText(
                requireContext(),
                "This device is not supported. Please install Google Play Services.",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun setupProfilePicture() {
        binding.imageViewProfile.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            
            if (name.isBlank()) {
                binding.editTextName.error = "Name cannot be empty"
                return@setOnClickListener
            }

            viewModel.updateProfile(name, email)
        }
    }

    private fun setupLogoutButton() {
        binding.buttonLogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun loadUserProfile() {
        viewModel.loadUserProfile()
        viewModel.userProfile.observe(viewLifecycleOwner) { user ->
            binding.editTextName.setText(user.name)
            binding.editTextEmail.setText(user.email)
            // Load profile picture if exists
            user.profilePictureUrl?.let { url ->
                binding.imageViewProfile.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.ic_person)
                    error(R.drawable.ic_person)
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.updateStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadProfilePicture(uri: Uri) {
        val userId = auth.currentUser?.uid ?: return
        val storageRef = storage.reference.child("profile_pictures/$userId.jpg")

        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    viewModel.updateProfilePicture(downloadUri.toString())
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 