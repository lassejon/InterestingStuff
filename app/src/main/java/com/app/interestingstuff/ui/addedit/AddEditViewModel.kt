package com.app.interestingstuff.ui.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.interestingstuff.data.InterestingItemRepository
import com.app.interestingstuff.model.InterestingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: InterestingItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Int = savedStateHandle["itemId"] ?: -1
    private val _item = MutableStateFlow<InterestingItem?>(null)
    val item: StateFlow<InterestingItem?> = _item

    private var currentImageUri: String? = null

    init {
        if (itemId != -1) {
            loadItem()
        }
    }

    private fun loadItem() {
        viewModelScope.launch {
            _item.value = repository.getItem(itemId)
            // Set the current image URI if exists
            _item.value?.imageUri?.let {
                currentImageUri = it
            }
        }
    }

    fun setImageUri(uri: String) {
        currentImageUri = uri
    }

    fun saveItem(title: String, description: String, rating: Float, imageUri: String? = null) {
        if (title.isBlank()) return

        viewModelScope.launch {
            val item = InterestingItem(
                id = itemId.takeIf { it != -1 } ?: 0,
                title = title,
                description = description,
                rating = rating,
                imageUri = imageUri ?: currentImageUri
            )

            if (itemId == -1) {
                repository.insertItem(item)
            } else {
                repository.updateItem(item)
            }
        }
    }
}