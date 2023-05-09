package com.example.testapp.di

import com.example.testapp.data.repository.CommentsRepositoryImpl
import com.example.testapp.data.repository.FavoriteRepositoryImpl
import com.example.testapp.data.repository.ProductsRepositoryImpl
import com.example.testapp.domain.faverites_post.FavoritesRepository
import com.example.testapp.domain.comments.CommentsRepository
import com.example.testapp.domain.posts.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideProductsRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): PostsRepository

    @Binds
    abstract fun provideCommentsRepository(
        commentsRepositoryImpl: CommentsRepositoryImpl
    ): CommentsRepository

    @Binds
    abstract fun provideFavoritesRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoritesRepository


}