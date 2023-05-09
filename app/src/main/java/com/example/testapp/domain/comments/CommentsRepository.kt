package com.example.testapp.domain.comments

import com.example.testapp.data.entity.comments.Comments


interface CommentsRepository {
    suspend fun getAllComments(id: Int): Comments
}