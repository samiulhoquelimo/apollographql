package com.droidturbo.apollographql.ui.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.droidturbo.apollographql.ui.base.BaseViewModel
import com.droidturbo.apollographql.ui.home.delegate.HomeViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeViewModelDelegate: HomeViewModelDelegate
) : BaseViewModel(), HomeViewModelDelegate by homeViewModelDelegate, DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        getCountries()
    }
}