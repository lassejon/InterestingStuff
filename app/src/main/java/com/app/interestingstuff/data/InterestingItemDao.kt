package com.app.interestingstuff.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.interestingstuff.model.InterestingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestingItemDao {
    @Query("SELECT * FROM interesting_items ORDER BY id DESC")
    fun getAllItems(): Flow<List<InterestingItem>>

    @Query("SELECT * FROM interesting_items WHERE id = :id")
    suspend fun getItemById(id: Int): InterestingItem?

    @Query("SELECT * FROM interesting_items WHERE id = :id")
    fun getItemFlow(id: Int): Flow<InterestingItem?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InterestingItem): Long

    @Update
    suspend fun updateItem(item: InterestingItem)

    @Delete
    suspend fun deleteItem(item: InterestingItem)
}