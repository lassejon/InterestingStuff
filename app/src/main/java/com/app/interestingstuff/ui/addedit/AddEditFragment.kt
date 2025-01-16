package com.app.interestingstuff.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.interestingstuff.databinding.FragmentAddEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditFragment : Fragment() {
    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddEditViewModel by viewModels()

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
                }
            }
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEdit.text.toString().trim()
            val description = binding.descriptionEdit.text.toString().trim()
            val rating = binding.ratingBar.rating

            if (validateInput(title, description)) {
                viewModel.saveItem(title, description, rating)
                findNavController().navigateUp()
            }
        }
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