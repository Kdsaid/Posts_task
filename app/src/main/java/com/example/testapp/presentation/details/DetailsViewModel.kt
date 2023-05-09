package com.example.testapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.entity.comments.Comments
import com.example.testapp.domain.comments.LoadAllCommentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val loadAllCommentsUseCase: LoadAllCommentsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState


    private var job: Job? = null

    fun loadData(id: Int) {
        job?.cancel()
        if (_uiState.value !is UiState.Loading) {
            _uiState.value = UiState.Loading
        }
        job = viewModelScope.launch {
            loadAllCommentsUseCase(id).onSuccess { items ->
                _uiState.value = UiState.Success(items)
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Unknown Error")
            }
        }

    }


    sealed class UiState {
        object Loading : UiState()
        class Error(val msg: String) : UiState()
        class Success(val products: Comments) : UiState()
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}