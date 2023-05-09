package com.example.testapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.domain.faverites_post.LoadFavoritePostUseCase
import com.example.testapp.domain.faverites_post.RemoveFavoritePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val removeFavoritePostUseCase: RemoveFavoritePostUseCase,
    private val loadFavoritePostUseCase: LoadFavoritePostUseCase,
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostsItem>>(emptyList())
    val posts: StateFlow<List<PostsItem>>
        get() = _posts

    fun loadFavoriteList() {
        viewModelScope.launch {
            loadFavoritePostUseCase(Unit).onSuccess {
                _posts.value = it

            }.onFailure {
                //TODO this case will ba handled later when Room DB
            }
        }
    }

    fun removePostsItemFromFavorite(postsItem: PostsItem) {
        viewModelScope.launch {
            removeFavoritePostUseCase(postsItem)
        }
    }

}