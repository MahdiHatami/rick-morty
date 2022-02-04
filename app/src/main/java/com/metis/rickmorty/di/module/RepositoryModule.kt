package com.metis.rickmorty.di.module

import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.data.source.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

  @Binds
  abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}
