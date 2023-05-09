package com.example.testapp.data.api

import com.example.testapp.configs.Constants
import com.example.testapp.data.entity.comments.Comments
import com.example.testapp.data.entity.posts.PostsItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun posts(): List<PostsItem>

    @GET("posts/{${Constants.ID}}/comments")
    suspend fun getComments(
        @Path(Constants.ID) id: Int
    ): Comments

}