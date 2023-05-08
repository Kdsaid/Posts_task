package com.example.testapp.data.api

import com.example.testapp.configs.Constants
import com.example.testapp.data.models.comments_model.CommentsModel
import com.example.testapp.data.models.posts_model.Posts
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun posts(): Posts

    @GET("posts/{${Constants.ID}}/comments")
    suspend fun getComments(
        @Path(Constants.ID) id: Int
    ): CommentsModel

}