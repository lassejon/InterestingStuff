package com.app.interestingstuff.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.interestingstuff.data.InterestingItemRepository
import com.app.interestingstuff.model.InterestingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: InterestingItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle["itemId"])
    private val _item = MutableStateFlow<InterestingItem?>(null)
    val item = repository.getItemFlow(itemId)

    init {
        loadItem()
    }

    private fun loadItem() {
        viewModelScope.launch {
            _item.value = repository.getItem(itemId)
        }
    }
}