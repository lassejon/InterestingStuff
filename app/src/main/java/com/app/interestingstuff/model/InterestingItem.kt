package com.app.interestingstuff.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interesting_items")
data class InterestingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val imageUri: String? = null,
    val rating: Float = 0f
)