package com.example.testapp.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.testapp.R
import com.example.testapp.databinding.FragmentFavoritesBinding
import com.example.testapp.utils.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private val binding by viewBinding<FragmentFavoritesBinding>()
    private val viewModel by viewModels<FavoritesViewModel>()
    private lateinit var favoritesAdapter: FavoritesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        favoritesAdapter = FavoritesAdapter(itemClick = {}, onFavouriteClick = {
            viewModel.removePostsItemFromFavorite(it)
            viewModel.loadFavoriteList()
        })

        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            postsRecycler.adapter = favoritesAdapter
            postsRecycler.setHasFixedSize(true)

        }
    }

    private fun observeUIState() = lifecycleScope.launch {
        viewModel.loadFavoriteList()
        viewModel.posts.flowWithLifecycle(lifecycle).collect {
            favoritesAdapter.submitList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        observeUIState()

    }
}