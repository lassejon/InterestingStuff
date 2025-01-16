package com.app.interestingstuff.model

data class InterestingItem(
    val id: Int = 0,
    val title: String,
    val description: String,
    val imageUri: String? = null,
    val rating: Float = 0f
)