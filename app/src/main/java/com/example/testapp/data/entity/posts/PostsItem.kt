package com.example.testapp.data.entity.posts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class PostsItem(
    val body: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val userId: Int,
    var isFave: Boolean = false,

    )