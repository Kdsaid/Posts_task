package com.example.testapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.domain.faverites_post.AddFavoritePostUseCase
import com.example.testapp.domain.faverites_post.LoadFavoritePostUseCase
import com.example.testapp.domain.faverites_post.RemoveFavoritePostUseCase
import com.example.testapp.domain.posts.LoadAllPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadAllPostsUseCase: LoadAllPostsUseCase,
    private val addFavoritePostUseCase: AddFavoritePostUseCase,
    private val removeFavoritePostUseCase: RemoveFavoritePostUseCase,
    private val loadFavoritePostUseCase: LoadFavoritePostUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState


    private var postsList = listOf<PostsItem>()

    init {
        loadData()
    }

    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        if (_uiState.value !is UiState.Loading) {
            _uiState.value = UiState.Loading
        }
        job = viewModelScope.launch {
            loadFavoritePostUseCase(Unit).onSuccess {

                postsList = it
            }.onFailure {

            }
            loadAllPostsUseCase(Unit).onSuccess { items ->
                val favoritePostsIds: List<Int> = postsList.map { it.id }
                val updatedProducts = items.map {
                    if (it.id in favoritePostsIds) {
                        it.copy(isFave = true)
                    } else {
                        it.copy(isFave = false)
                    }
                }
                _uiState.value = UiState.Success(updatedProducts.toList())
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Unknown Error")
            }
        }

    }

    fun onUpdateFavoritesPost(postsItem: PostsItem) {

        viewModelScope.launch {
            postsItem.isFave = !postsItem.isFave
            if (postsItem.isFave) addFavoritePostUseCase(postsItem)
            else removeFavoritePostUseCase(postsItem)
        }
    }

    sealed class UiState {
        object Loading : UiState()
        class Error(val msg: String) : UiState()
        class Success(val postsItem: List<PostsItem>) : UiState()
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}