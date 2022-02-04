package com.metis.rickmorty.di.module

import com.metis.rickmorty.data.source.local.LocalDataSource
import com.metis.rickmorty.data.source.local.LocalDataSourceImpl
import com.metis.rickmorty.data.source.remote.RemoteDataSource
import com.metis.rickmorty.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourcesModule {

  @Binds
  abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

  @Binds
  abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}
