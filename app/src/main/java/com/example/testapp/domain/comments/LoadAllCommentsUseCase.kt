package com.example.testapp.domain.comments

import com.example.testapp.data.entity.comments.Comments
import com.example.testapp.di.DefaultDispatcher
import com.example.testapp.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadAllCommentsUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val commentsRepository: CommentsRepository,
) : SuspendUseCase<Int, Comments>(dispatcher) {

    override suspend fun execute(id: Int): Comments {

        return commentsRepository.getAllComments(id = id)
    }
}