package com.droidturbo.apollographql.ui.home.delegate

import com.droidturbo.apollographql.data.model.DetailedCountry
import com.droidturbo.apollographql.data.model.SimpleCountry
import com.droidturbo.apollographql.di.qualifier.ApplicationScope
import com.droidturbo.apollographql.ui.home.datasource.HomeDataSource
import com.droidturbo.apollographql.utils.arch.Result
import com.droidturbo.apollographql.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelDelegate {
    val countries: Flow<Result<List<SimpleCountry>>>
    val country: Flow<Result<DetailedCountry>>
    fun getCountries()
    fun getCountry(code: String)
}

internal class HomeViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val homeDataSource: HomeDataSource
) : HomeViewModelDelegate {

    private val _countries = Channel<Result<List<SimpleCountry>>>(Channel.CONFLATED)
    override val countries = _countries.receiveAsFlow()

    private val _country = Channel<Result<DetailedCountry>>(Channel.CONFLATED)
    override val country = _country.receiveAsFlow()

    override fun getCountries() {
        applicationScope.launch {
            homeDataSource.getCountries().collect { result ->
                _countries.tryOffer(result)
            }
        }
    }

    override fun getCountry(code: String) {
        applicationScope.launch {
            homeDataSource.getCountry(code = code).collect { result ->
                _country.tryOffer(result)
            }
        }
    }
}