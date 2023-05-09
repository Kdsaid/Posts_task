package com.example.testapp.domain.faverites_post

import com.example.testapp.data.entity.posts.PostsItem


interface FavoritesRepository {
    suspend fun getFavoriteProducts(): List<PostsItem>
    suspend fun addFavoriteProduct(postsItem: PostsItem)
    suspend fun removeFavoriteProduct(postsItem: PostsItem)
}