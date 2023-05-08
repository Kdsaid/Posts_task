package com.example.testapp.data.local

import androidx.room.*
import com.example.testapp.data.models.posts_model.PostsItem

@Dao
interface PostsDao {
    @get:Query("SELECT * FROM PostsItem")
    val all: List<PostsItem?>?

    @Query("select * from PostsItem where id like :id")
    fun fetchById(id: Int): PostsItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(insert: PostsItem?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(update: PostsItem?)

    @Delete
    fun delete(delete: PostsItem?)

    @Query("delete from PostsItem")
    fun deleteAll()
}