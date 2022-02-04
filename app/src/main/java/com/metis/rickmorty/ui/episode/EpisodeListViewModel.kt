package com.metis.rickmorty.ui.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult
import com.metis.rickmorty.ui.mapper.toViewEpisodeItem
import com.metis.rickmorty.utils.LoadingState
import com.metis.rickmorty.utils.StatusProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeListViewModel  @Inject constructor(
  private val repository: Repository,
  private val statusProvider: StatusProvider
) : ViewModel() {
  private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.None)
  val loadingState: StateFlow<LoadingState> = _loadingState

  private val _isOffline: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val isOffline: StateFlow<Boolean> = _isOffline

  private val _isEndOfList: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val isEndOfList: StateFlow<Boolean> = _isEndOfList

  private val _onError: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val onError: StateFlow<Boolean> = _onError

  private val _episodes: MutableStateFlow<List<ViewEpisodeItem>> = MutableStateFlow(emptyList())
  val episodes: StateFlow<List<ViewEpisodeItem>> = _episodes

  private val _selectedEpisodeCharacterIds: Channel<IntArray> = Channel()
  val selectedEpisodeCharacterIds: Flow<IntArray> = _selectedEpisodeCharacterIds.receiveAsFlow()

  private var page = 1

  init {
    loadEpisodes()
  }

  fun loadEpisodes() {
    _loadingState.value = LoadingState.Loading
    _isEndOfList.value = false
    _onError.value = false
    _isOffline.value = false
    page = 1
    getEpisodes()
  }

  private fun getEpisodes() {
    if (!statusProvider.isOnline()) {
      _isOffline.value = true
    }

    viewModelScope.launch {
      when (val result = repository.getEpisodes(page)) {
        is PageQueryResult.Successful -> {
          if (page == 1) {
            _episodes.value = result.data.toViewEpisodeItems()
          } else {
            _episodes.value = _episodes.value + result.data.toViewEpisodeItems()
          }
        }
        PageQueryResult.EndOfList -> _isEndOfList.value = true
        PageQueryResult.Error -> _onError.value = true
      }
      _loadingState.value = LoadingState.None
    }
  }

  private fun List<ModelEpisode>.toViewEpisodeItems(): List<ViewEpisodeItem> = map { episode ->
    episode.toViewEpisodeItem {
      _selectedEpisodeCharacterIds.trySend(episode.characterIds.toIntArray())
    }
  }

  fun loadMoreEpisodes(page: Int) {
    _loadingState.value = LoadingState.LoadingMore
    this.page = page
    getEpisodes()
  }
}