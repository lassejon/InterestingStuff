package com.app.interestingstuff.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.interestingstuff.databinding.FragmentDetailBinding
import com.app.interestingstuff.model.InterestingItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()
    private var currentItem: InterestingItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.item.collectLatest { item ->
                item?.let {
                    currentItem = it  // Store the current item
                    binding.titleText.text = it.title
                    binding.descriptionText.text = it.description
                    binding.ratingBar.rating = it.rating

                    // Set image if available
                    it.imageUri?.let { uri ->
                        binding.imageView.setImageURI(Uri.parse(uri))
                    }
                }
            }
        }

        binding.editFab.setOnClickListener {
            currentItem?.let { item ->
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToAddEditFragment(item.id)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}