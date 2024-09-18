package com.droidturbo.apollographql.ui.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object HomeActivityModule {

    @Provides
    internal fun provideHomeAdapter(): HomeAdapter = HomeAdapter()

}