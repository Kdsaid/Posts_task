package com.example.testapp.data.models.posts_model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostsItem(
    val body: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val userId: Int
)