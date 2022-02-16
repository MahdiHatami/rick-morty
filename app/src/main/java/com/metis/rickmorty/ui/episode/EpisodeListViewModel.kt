package com.metis.rickmorty.ui.episode

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult
import com.metis.rickmorty.ui.mapper.toViewEpisodeItem
import com.metis.rickmorty.utils.StatusProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(
  private val repository: Repository,
  private val statusProvider: StatusProvider
) : ViewModel() {

  private val _uiState: MutableState<EpisodeListUiState> = mutableStateOf(EpisodeListUiState())
  val uiState: State<EpisodeListUiState> = _uiState

  private val _selectedEpisodeCharacterIds: Channel<IntArray> = Channel()
  val selectedEpisodeCharacterIds: Flow<IntArray> = _selectedEpisodeCharacterIds.receiveAsFlow()

  private var page = 1

  init {
    loadEpisodes()
  }

  private fun loadEpisodes() {
    _uiState.value = EpisodeListUiState()
    page = 1
    getEpisodes()
  }

  private fun getEpisodes() {
    if (!statusProvider.isOnline()) {
      _uiState.value = _uiState.value.copy(isOffline = true)
    }

    viewModelScope.launch {
      when (val result = repository.getEpisodes(page)) {
        is PageQueryResult.Successful -> {
          if (page == 1) {
            _uiState.value = _uiState.value.copy(episodes = result.data.toViewEpisodeItems())
          } else {
            _uiState.value =
              _uiState.value.copy(episodes = _uiState.value.episodes + result.data.toViewEpisodeItems())
          }
        }
        PageQueryResult.EndOfList ->  _uiState.value = _uiState.value.copy(isEndOfList = true)
        PageQueryResult.Error -> _uiState.value = _uiState.value.copy(onError = true)
      }
      _uiState.value = _uiState.value.copy(loading = false)
    }
  }

  private fun List<ModelEpisode>.toViewEpisodeItems(): List<ViewEpisodeItem> = map { episode ->
    episode.toViewEpisodeItem {
      _selectedEpisodeCharacterIds.trySend(episode.characterIds.toIntArray())
    }
  }

  fun loadMoreEpisodes(page: Int) {
    _uiState.value = _uiState.value.copy(loadingMore = true)
    this.page = page
    getEpisodes()
  }
}