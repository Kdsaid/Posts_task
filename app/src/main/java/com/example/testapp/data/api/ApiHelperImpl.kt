package com.example.testapp.data.api


import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {


    override fun postsFlow() = flow { emit(apiService.posts()) }
    override fun getComments(id: Int) = flow { emit(apiService.getComments(id = id)) }


}