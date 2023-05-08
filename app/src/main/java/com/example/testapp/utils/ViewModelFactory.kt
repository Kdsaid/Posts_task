package com.example.testapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.data.api.ApiHelper
import com.example.testapp.data.models.comments_model.CommentsModel
import com.example.testapp.ui.details.DetailsViewModel
import com.example.testapp.ui.home.HomeViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val id: Int = 0) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(apiHelper) as T
        }

        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(apiHelper, id = id) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}