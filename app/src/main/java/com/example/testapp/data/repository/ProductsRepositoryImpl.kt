package com.example.testapp.data.repository

import com.example.testapp.data.api.ApiService
import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.domain.posts.PostsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsAPi: ApiService
) : PostsRepository {
    override suspend fun getAllPosts(): List<PostsItem> {
        return productsAPi.posts()
    }
}