package com.app.interestingstuff.data

import com.app.interestingstuff.model.InterestingItem
import kotlinx.coroutines.flow.Flow

class InterestingItemRepository(private val interestingItemDao: InterestingItemDao) {

    val allItems: Flow<List<InterestingItem>> = interestingItemDao.getAllItems()

    suspend fun getItem(id: Int): InterestingItem? {
        return interestingItemDao.getItemById(id)
    }

    fun getItemFlow(id: Int): Flow<InterestingItem?> {
        return interestingItemDao.getItemFlow(id)
    }

    suspend fun insertItem(item: InterestingItem): Long {
        return interestingItemDao.insertItem(item)
    }

    suspend fun updateItem(item: InterestingItem) {
        interestingItemDao.updateItem(item)
    }

    suspend fun deleteItem(item: InterestingItem) {
        interestingItemDao.deleteItem(item)
    }
}