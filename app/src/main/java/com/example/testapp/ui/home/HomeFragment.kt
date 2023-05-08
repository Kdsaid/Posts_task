package com.example.testapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.footballleagueapplication.utils.Status
import com.example.footballleagueapplication.utils.animateItems
import com.example.footballleagueapplication.utils.hide
import com.example.footballleagueapplication.utils.show
import com.example.testapp.utils.ViewModelFactory
import com.example.footballleagueapplication.utils.toast
import com.example.testapp.data.api.ApiHelperImpl
import com.example.testapp.data.api.RetrofitBuilder
import com.example.testapp.data.models.posts_model.Posts
import com.example.testapp.databinding.FragmentHomeBinding
import com.example.testapp.utils.navigateTo

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(
            this, ViewModelFactory(
                ApiHelperImpl((RetrofitBuilder.apiService)),
            )
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayHomeData()

    }

    private fun displayHomeData() {

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.leagues.collect {
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

    private fun setAdsData(getData: Posts) {
        binding.postsRecycler.apply {

            adapter = HomeDataAdapter(getData) { id ->
                navigateTo(HomeFragmentDirections.actionNavigationHomeToNavigationNotifications(id = id))
            }
            animateItems()
            scheduleLayoutAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}