package com.example.testapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballleagueapplication.utils.Resource
import com.example.testapp.data.api.ApiHelper
import com.example.testapp.data.models.posts_model.Posts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ApiHelper) : ViewModel() {
    private val _mutableStateFlow = MutableStateFlow<Resource<Posts>>(Resource.loading())
    val leagues: StateFlow<Resource<Posts>> = _mutableStateFlow

    init {
        getLeagues()
    }

    private fun getLeagues() {


        viewModelScope.launch {
            _mutableStateFlow.value = Resource.loading()
            repository.postsFlow().flowOn(Dispatchers.IO).catch { e ->
                _mutableStateFlow.value = Resource.error(data = null, message = e.toString())
            }.collect {
                _mutableStateFlow.value = Resource.success(it)
            }

        }
    }
}







