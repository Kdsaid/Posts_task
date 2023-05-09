package com.example.testapp.domain.faverites_post

import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.di.DefaultDispatcher
import com.example.testapp.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadFavoritePostUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val wishListRepository: FavoritesRepository,
) : SuspendUseCase<Unit, List<PostsItem>>(dispatcher) {
    override suspend fun execute(i: Unit): List<PostsItem> {
        return wishListRepository.getFavoriteProducts()
    }
}