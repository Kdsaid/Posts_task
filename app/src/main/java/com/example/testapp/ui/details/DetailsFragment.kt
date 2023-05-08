package com.example.testapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.footballleagueapplication.utils.*
import com.example.testapp.data.api.ApiHelperImpl
import com.example.testapp.data.api.RetrofitBuilder
import com.example.testapp.data.models.comments_model.CommentsModel
import com.example.testapp.databinding.DetailsFragmentBinding
import com.example.testapp.utils.ViewModelFactory

class DetailsFragment : Fragment() {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val safeArgs by lazy {
        DetailsFragmentArgs.fromBundle(
            requireArguments()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        displayHomeData(safeArgs.id)

    }

    private fun displayHomeData(commentId: Int) {
        val teamDetailsViewModel by lazy {
            ViewModelProvider(
                this, ViewModelFactory(ApiHelperImpl((RetrofitBuilder.apiService)), commentId)
            )[DetailsViewModel::class.java]
        }

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                teamDetailsViewModel.commentsDetails.collect {
                    when (it.status) {
                        Status.SUCCESS -> it.data?.let { getData ->
                            binding.loader.progressBar.hide()
                            setAdsData(getData)

                        }

                        Status.ERROR -> {
                            binding.loader.progressBar.hide()
                            context?.toast("Something went wrong, try later")
                        }

                        Status.LOADING -> {
                            binding.loader.progressBar.show()
                        }
                    }
                }
            }
        }

    }

    private fun setAdsData(getData: CommentsModel) {

        binding.rvTeamPlayer.apply {
            adapter = DetailsAdapter(getData)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}


