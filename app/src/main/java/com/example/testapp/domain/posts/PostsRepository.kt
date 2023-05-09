package com.example.testapp.domain.posts

import com.example.testapp.data.entity.posts.PostsItem


interface PostsRepository {
    suspend fun getAllPosts(): List<PostsItem>
}