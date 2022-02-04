package com.metis.rickmorty.di.module

import com.metis.rickmorty.di.scope.DefaultDispatcher
import com.metis.rickmorty.di.scope.IoDispatcher
import com.metis.rickmorty.di.scope.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object DispatcherModule {

  @Provides
  @DefaultDispatcher
  fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Provides
  @IoDispatcher
  fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @MainDispatcher
  fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
