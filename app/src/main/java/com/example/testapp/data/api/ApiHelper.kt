package com.example.testapp.data.api

import com.example.testapp.data.models.comments_model.CommentsModel
import com.example.testapp.data.models.posts_model.Posts
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun postsFlow(): Flow<Posts>
    fun getComments(id: Int): Flow<CommentsModel>

}