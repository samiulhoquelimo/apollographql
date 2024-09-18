package com.droidturbo.apollographql.ui.home.datasource

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.droidturbo.CountriesQuery
import com.droidturbo.CountryQuery
import com.droidturbo.apollographql.R
import com.droidturbo.apollographql.data.ext.toDetailedCountry
import com.droidturbo.apollographql.data.ext.toSimpleCountry
import com.droidturbo.apollographql.data.model.DetailedCountry
import com.droidturbo.apollographql.data.model.SimpleCountry
import com.droidturbo.apollographql.di.qualifier.IoDispatcher
import com.droidturbo.apollographql.utils.UiText
import com.droidturbo.apollographql.utils.arch.Result
import com.droidturbo.apollographql.utils.extension.isNetworkConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface HomeDataSource {
    suspend fun getCountries(): Flow<Result<List<SimpleCountry>>>
    suspend fun getCountry(
        code: String
    ): Flow<Result<DetailedCountry>>
}

class HomeDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apolloClient: ApolloClient
) : HomeDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override suspend fun getCountries(): Flow<Result<List<SimpleCountry>>> = flow {
        try {
            emit(Result.Loading())
            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }
            val list = apolloClient
                .query(CountriesQuery())
                .execute()
                .data
                ?.countries
                ?.map { it.toSimpleCountry() }
                ?: emptyList()
            emit(Result.Success(list))
        } catch (e: Exception) {
            emit(Result.Error(UiText.DynamicString(e.message)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)

    override suspend fun getCountry(code: String): Flow<Result<DetailedCountry>> = flow {
        try {
            emit(Result.Loading())
            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }
            apolloClient
                .query(CountryQuery(code))
                .execute()
                .data
                ?.country
                ?.toDetailedCountry()?.let { detailedCountry ->
                    emit(Result.Success(detailedCountry))
                } ?: run {
                emit(Result.Error(UiText.StringResource(R.string.not_found)))
            }
        } catch (e: Exception) {
            emit(Result.Error(UiText.DynamicString(e.message)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)
}