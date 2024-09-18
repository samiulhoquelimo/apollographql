package com.droidturbo.apollographql.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.droidturbo.apollographql.data.model.DetailedCountry
import com.droidturbo.apollographql.databinding.DialogHomeBinding
import com.droidturbo.apollographql.ui.base.BaseDialogFragment
import com.droidturbo.apollographql.utils.arch.Result
import com.droidturbo.apollographql.utils.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeDialog : BaseDialogFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private var _binding: DialogHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_CODE = "code"

        const val TAG: String = "HomeDialog"
        fun newInstance(code: String): HomeDialog = HomeDialog().apply {
            arguments = bundleOf(KEY_CODE to code)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun setup(view: View) {
        getCode()?.let { code -> viewModel.getCountry(code) }

        binding.btDismiss.setOnClickListener { dismiss() }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.country.collect { result ->
                        when (result) {
                            is Result.Error -> toast(result.uiText)
                            is Result.Loading -> loading(result.isLoading)
                            is Result.Success -> parserData(result.data)
                        }
                    }
                }
            }
        }
    }

    private fun parserData(data: DetailedCountry) {
        val message = "code: ${data.code}\n" +
                "name: ${data.name}\n" +
                "emoji: ${data.emoji}\n" +
                "capital: ${data.capital}\n" +
                "currency: ${data.currency}\n" +
                "languages: ${data.languages}\n" +
                "continent: ${data.continent}"
        binding.tvDetails.text = message
    }

    private fun loading(isLoading: Boolean) {
        binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getCode(): String? = arguments?.getString(KEY_CODE)

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}