package com.app.interestingstuff.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.interestingstuff.data.InterestingItemRepository
import com.app.interestingstuff.model.InterestingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: InterestingItemRepository
) : ViewModel() {
    val items: StateFlow<List<InterestingItem>> = repository.allItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteItem(item: InterestingItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun insertTestData() {
        viewModelScope.launch {
            val testItems = listOf(
                InterestingItem(
                    title = "Mountain Bike",
                    description = "Amazing mountain bike with full suspension",
                    rating = 4.5f
                ),
                InterestingItem(
                    title = "Vintage Camera",
                    description = "Classic Leica M3 from 1954",
                    rating = 5.0f
                ),
                InterestingItem(
                    title = "Programming Book",
                    description = "Clean Code by Robert C. Martin",
                    rating = 4.8f
                )
            )

            testItems.forEach { item ->
                repository.insertItem(item)
            }
        }
    }

    fun insertItem(item: InterestingItem) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }
}