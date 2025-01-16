package com.app.interestingstuff.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.interestingstuff.databinding.FragmentListBinding
import com.app.interestingstuff.model.InterestingItem

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: InterestingItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFab()

        // Load test data
        loadTestData()
    }

    private fun setupRecyclerView() {
        adapter = InterestingItemAdapter { item ->
            // Handle item click - we'll implement navigation later
            // findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(item.id))
        }
        binding.recyclerView.adapter = adapter
    }

    private fun setupFab() {
        binding.fabAdd.setOnClickListener {
            // We'll implement navigation to add item screen later
            // findNavController().navigate(ListFragmentDirections.actionListFragmentToAddEditFragment())
        }
    }

    private fun loadTestData() {
        // Create some test items
        val testItems = listOf(
            InterestingItem(
                id = 1,
                title = "Mountain Bike",
                description = "A fantastic mountain bike I found in the local shop",
                rating = 4.5f
            ),
            InterestingItem(
                id = 2,
                title = "Vintage Camera",
                description = "Beautiful old Leica camera from the 1950s",
                rating = 5.0f
            ),
            InterestingItem(
                id = 3,
                title = "Art Exhibition",
                description = "Modern art exhibition at the city gallery",
                rating = 3.5f
            )
        )

        // Submit the list to the adapter
        adapter.submitList(testItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}