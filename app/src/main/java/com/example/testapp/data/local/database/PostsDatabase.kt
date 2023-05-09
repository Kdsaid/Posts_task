package com.example.testapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.configs.Constants
import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.data.local.dao.PostDao

@Database(
    entities = [PostsItem::class],
    version = Constants.DB_VERSION,
    exportSchema = false
)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postsDao(): PostDao
}
