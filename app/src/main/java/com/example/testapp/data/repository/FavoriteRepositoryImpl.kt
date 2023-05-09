package com.example.testapp.data.repository

import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.data.local.database.PostsDatabase
import com.example.testapp.domain.faverites_post.FavoritesRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val postsDatabase: PostsDatabase) :
    FavoritesRepository {
    override suspend fun getFavoriteProducts(): List<PostsItem> {
        return postsDatabase.postsDao().getPostsItem()
    }

    override suspend fun addFavoriteProduct(postsItem: PostsItem) {
        postsDatabase.postsDao().bookmarkPostsItem(postsItem)

    }

    override suspend fun removeFavoriteProduct(postsItem: PostsItem) {
        postsDatabase.postsDao().unBookmarkPostsItem(postsItem)
    }
}