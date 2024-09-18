package com.droidturbo.apollographql.ui.home

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.droidturbo.apollographql.data.model.SimpleCountry
import com.droidturbo.apollographql.databinding.ActivityHomeBinding
import com.droidturbo.apollographql.ui.base.BaseActivity
import com.droidturbo.apollographql.utils.arch.Result
import com.droidturbo.apollographql.utils.extension.clicked
import com.droidturbo.apollographql.utils.extension.loading
import com.droidturbo.apollographql.utils.extension.toast
import com.droidturbo.apollographql.utils.extension.with
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    @Inject
    lateinit var adapter: HomeAdapter

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding

    override fun getLayoutResourceId(): View {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        lifecycle.addObserver(viewModel)

        binding.listHome.with(adapter.apply { clicked(::clicked) })
        binding.swipeRefreshLayout.clicked { viewModel.getCountries() }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.countries.collect { result ->
                        when (result) {
                            is Result.Error -> toast(result.uiText)
                            is Result.Loading -> binding.swipeRefreshLayout.loading(result.isLoading)
                            is Result.Success -> parserData(result.data)
                        }
                    }
                }
            }
        }
    }

    private fun parserData(data: List<SimpleCountry>) {
        adapter.submitList(data)
    }

    private fun clicked(code: String) {
        val dialog = HomeDialog.newInstance(code)
        dialog.show(supportFragmentManager, HomeDialog.TAG)
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }
}
