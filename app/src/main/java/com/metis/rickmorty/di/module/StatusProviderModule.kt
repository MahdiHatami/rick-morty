package com.metis.rickmorty.di.module

import android.content.Context
import com.metis.rickmorty.utils.StatusProvider
import com.metis.rickmorty.utils.StatusProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StatusProviderModule {

    @Provides
    @Singleton
    fun bindStatusProvider(context: Context): StatusProvider {
        return StatusProviderImpl(context)
    }
}
