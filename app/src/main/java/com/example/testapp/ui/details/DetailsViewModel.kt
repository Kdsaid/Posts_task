package com.example.testapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballleagueapplication.utils.Resource
import com.example.testapp.data.api.ApiHelper
import com.example.testapp.data.models.comments_model.CommentsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: ApiHelper, id: Int) : ViewModel() {
    private val _mutableStateFlow =
        MutableStateFlow<Resource<CommentsModel>>(Resource.loading())
    val commentsDetails: StateFlow<Resource<CommentsModel>> = _mutableStateFlow

    init {
        getTeamDetails(id = id)
    }

    private fun getTeamDetails(
        id: Int

    ) {
        viewModelScope.launch {
            _mutableStateFlow.value = Resource.loading()
            repository.getComments(id = id).flowOn(Dispatchers.IO).catch { e ->
                _mutableStateFlow.value = Resource.error(data = null, message = e.toString())
            }.collect {
                _mutableStateFlow.value = Resource.success(it)
            }

        }


    }
}