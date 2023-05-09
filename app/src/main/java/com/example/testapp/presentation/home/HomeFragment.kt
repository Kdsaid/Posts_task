package com.example.testapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.footballleagueapplication.utils.animateItems
import com.example.footballleagueapplication.utils.hide
import com.example.footballleagueapplication.utils.show
import com.example.footballleagueapplication.utils.toast
import com.example.testapp.R
import com.example.testapp.databinding.FragmentHomeBinding
import com.example.testapp.utils.common.EmptyStatePresenter
import com.example.testapp.utils.common.snackRetry
import com.example.testapp.utils.common.viewBinding
import com.example.testapp.utils.navigateTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), EmptyStatePresenter {
    override fun getEmptyStateLayout() = binding.emptyLayout
    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var homeDataAdapter: HomeDataAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            postsRecycler.apply {
                homeDataAdapter = HomeDataAdapter(itemClick = {
                    navigateTo(
                        HomeFragmentDirections.actionNavigationHomeToNavigationNotifications(
                            it
                        )
                    )

                }, onFavouriteClick = {
                    requireContext().toast(it.toString())
                    viewModel.onUpdateFavoritesPost(it)
                })
                adapter = homeDataAdapter
                animateItems()
                scheduleLayoutAnimation()
                setHasFixedSize(true)
            }
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.loadData()
            }
        }
    }

    private fun observeUIState() = lifecycleScope.launch {
        viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
    }


    private fun updateUI(uiState: HomeViewModel.UiState) {
        when (uiState) {
            is HomeViewModel.UiState.Error -> {
                binding.loader.progressBar.hide()

                binding.swipeRefreshLayout.isRefreshing = false
                if (homeDataAdapter.itemCount > 0) {
                    snackRetry(
                        "No Internet connection! Please try again.", callback = viewModel::loadData
                    )
                } else {
                    showNetworkError(viewModel::loadData)
                }
            }

            is HomeViewModel.UiState.Loading -> {
                binding.loader.progressBar.show()
                binding.swipeRefreshLayout.isRefreshing = true
            }


            is HomeViewModel.UiState.Success -> {
                binding.loader.progressBar.hide()
                binding.swipeRefreshLayout.isRefreshing = false
                if (uiState.postsItem.isNotEmpty()) {
                    showContent()
                } else {
                    showEmptyState("No Cakes data available right now!")
                }
                homeDataAdapter.submitList(uiState.postsItem)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        observeUIState()

    }
}