package com.app.interestingstuff.ui.addedit

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.interestingstuff.R
import com.app.interestingstuff.databinding.FragmentAddEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddEditFragment : Fragment() {
    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddEditViewModel by viewModels()
    private var currentImageUri: Uri? = null

    // Simplified permission launcher that shows the system permission dialog
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            takePhoto()
        } else {
            Toast.makeText(
                requireContext(),
                "Camera permission denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Photo capture launcher
    private val takePhotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            currentImageUri?.let { uri ->
                view?.findViewById<ImageView>(R.id.imageView)?.setImageURI(uri)
                viewModel.setImageUri(uri.toString())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe existing item data if editing
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.item.collect { item ->
                item?.let {
                    binding.titleEdit.setText(it.title)
                    binding.descriptionEdit.setText(it.description)
                    binding.ratingBar.rating = it.rating

                    // Load existing image if available
                    it.imageUri?.let { uri ->
                        view.findViewById<ImageView>(R.id.imageView)?.setImageURI(Uri.parse(uri))
                    }
                }
            }
        }

        // Setup camera button
        view.findViewById<Button>(R.id.captureImageButton)?.setOnClickListener {
            requestCameraPermission()
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEdit.text.toString().trim()
            val description = binding.descriptionEdit.text.toString().trim()
            val rating = binding.ratingBar.rating

            if (validateInput(title, description)) {
                // Get the image URI, either from currentImageUri or from the existing item
                val imageUri = currentImageUri?.toString()
                    ?: viewModel.item.value?.imageUri

                viewModel.saveItem(title, description, rating, imageUri)
                findNavController().navigateUp()
            }
        }
    }

    private fun requestCameraPermission() {
        when {
            // Permission already granted, directly take photo
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                takePhoto()
            }
            // Show standard system permission request dialog
            else -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun takePhoto() {
        val photoFile = createImageFile()
        currentImageUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            photoFile
        )
        takePhotoLauncher.launch(currentImageUri)
    }

    private fun createImageFile(): File {
        val storageDir = requireContext().getExternalFilesDir(null)
        return File.createTempFile(
            "JPEG_${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        )
    }

    private fun validateInput(title: String, description: String): Boolean {
        var isValid = true

        if (title.isEmpty()) {
            binding.titleLayout.error = "Title is required"
            isValid = false
        } else {
            binding.titleLayout.error = null
        }

        if (description.isEmpty()) {
            binding.descriptionLayout.error = "Description is required"
            isValid = false
        } else {
            binding.descriptionLayout.error = null
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}