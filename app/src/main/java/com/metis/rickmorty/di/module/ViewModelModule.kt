package com.metis.rickmorty.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metis.rickmorty.ViewModelFactory
import com.metis.rickmorty.di.key.ViewModelKey
import com.metis.rickmorty.ui.character.CharacterListViewModel
import com.metis.rickmorty.ui.characterDetail.CharacterDetailsViewModel
import com.metis.rickmorty.ui.episode.EpisodeListViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
abstract class ViewModelModule {

  @Binds
  abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

  @IntoMap
  @Binds
  @ViewModelKey(EpisodeListViewModel::class)
  abstract fun bindEpisodeListViewModel(viewModel: EpisodeListViewModel): ViewModel

  @IntoMap
  @Binds
  @ViewModelKey(CharacterListViewModel::class)
  abstract fun bindCharacterListViewModel(viewModel: CharacterListViewModel): ViewModel

  @IntoMap
  @Binds
  @ViewModelKey(CharacterDetailsViewModel::class)
  abstract fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel
}
