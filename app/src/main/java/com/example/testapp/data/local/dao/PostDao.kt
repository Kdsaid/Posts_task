package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp.data.entity.posts.PostsItem

@Dao
interface PostDao {

    @Query("SELECT * FROM PostsItem")
    fun getPostsItem(): List<PostsItem>

    @Query("select * from PostsItem where id like :id")
    fun getPostsItemById(id: Int): PostsItem

    @Query("DELETE FROM PostsItem")
    fun clearPostsItem(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bookmarkPostsItem(PostsItem: PostsItem)

    @Delete
    fun unBookmarkPostsItem(PostsItem: PostsItem)


}
