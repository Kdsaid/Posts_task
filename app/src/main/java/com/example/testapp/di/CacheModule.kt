package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.configs.Constants
import com.example.testapp.data.local.dao.PostDao
import com.example.testapp.data.local.database.PostsDatabase
//import com.example.testapp.data.local.dao.PostDao
//import com.example.testapp.data.local.database.PostsDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {


    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): PostsDatabase =
        Room.databaseBuilder(context, PostsDatabase::class.java, Constants.DB_NAME)
            .build()

    @Provides
    fun providesPostDao(postDatabase: PostsDatabase): PostDao =
        postDatabase.postsDao()


}
