package com.example.testapp.data.repository

import com.example.testapp.data.api.ApiService
import com.example.testapp.data.entity.comments.Comments
import com.example.testapp.domain.comments.CommentsRepository
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CommentsRepository {
    override suspend fun getAllComments(id: Int): Comments {
        return apiService.getComments(id = id)
    }
}