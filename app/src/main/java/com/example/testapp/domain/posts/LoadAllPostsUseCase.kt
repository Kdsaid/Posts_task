package com.example.testapp.domain.posts

import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.di.DefaultDispatcher
import com.example.testapp.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadAllPostsUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val postsRepository: PostsRepository,

    ) : SuspendUseCase<Unit, List<PostsItem>>(dispatcher) {

    override suspend fun execute(i: Unit): List<PostsItem> {

        return postsRepository.getAllPosts()
    }
}