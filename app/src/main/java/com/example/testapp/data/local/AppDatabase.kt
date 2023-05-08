package com.example.testapp.data.local

import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.testapp.configs.AppConfig
import com.example.testapp.data.models.posts_model.PostsItem

@Database(entities = [PostsItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): PostsDao?
    fun destroyInstance() {
        INSTANCE = null
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = databaseBuilder(
                        AppConfig.instance.applicationContext,
                        AppDatabase::class.java,
                        "PostsDaoDB"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}