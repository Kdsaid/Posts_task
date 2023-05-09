package com.example.testapp.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.footballleagueapplication.utils.animateItems
import com.example.footballleagueapplication.utils.hide
import com.example.footballleagueapplication.utils.show
import com.example.testapp.R
import com.example.testapp.databinding.DetailsFragmentBinding
import com.example.testapp.utils.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val binding by viewBinding<DetailsFragmentBinding>()
    private val viewModel by viewModels<DetailsViewModel>()
    private lateinit var detailsAdapter: DetailsAdapter


    private val safeArgs by lazy {
        DetailsFragmentArgs.fromBundle(
            requireArguments()
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvTeamPlayer.apply {
                detailsAdapter = DetailsAdapter()
                adapter = detailsAdapter
                animateItems()
                scheduleLayoutAnimation()
                setHasFixedSize(true)
            }
        }

        observeUIState()

    }

    private fun observeUIState() = lifecycleScope.launch {
        viewModel.loadData(safeArgs.id)
        viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
    }


    private fun updateUI(uiState: DetailsViewModel.UiState) {
        when (uiState) {
            is DetailsViewModel.UiState.Error -> {
                binding.loader.progressBar.hide()


            }

            is DetailsViewModel.UiState.Loading -> {
                binding.loader.progressBar.show()
            }


            is DetailsViewModel.UiState.Success -> {
                binding.loader.progressBar.hide()

                detailsAdapter.submitList(uiState.products)
            }

        }
    }


}


