package com.metis.rickmorty.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.domain.model.ModelCharacter
import com.metis.rickmorty.domain.model.QueryResult
import com.metis.rickmorty.ui.mapper.toViewCharacterItem
import com.metis.rickmorty.utils.LoadingState
import com.metis.rickmorty.utils.StatusProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(
  private val repository: Repository,
  private val statusProvider: StatusProvider,
) : ViewModel() {

  private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.None)
  val loadingState: StateFlow<LoadingState> = _loadingState

  private val _isOffline: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val isOffline: StateFlow<Boolean> = _isOffline

  private val _isNoCache: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val isNoCache: StateFlow<Boolean> = _isNoCache

  private val _onError: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val onError: StateFlow<Boolean> = _onError

  private val _characters: MutableStateFlow<List<ViewCharacterItem>> = MutableStateFlow(emptyList())
  val characters: StateFlow<List<ViewCharacterItem>> = _characters

  private val _selectedCharacterId: Channel<Int> = Channel()
  val selectedCharacterId: Flow<Int> = _selectedCharacterId.receiveAsFlow()

  private val _characterIds: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
  val characterIds: StateFlow<List<Int>> = _characterIds

  fun loadCharacters(characterIds: List<Int>) {
    _loadingState.value = LoadingState.Loading
    _onError.value = false
    _isOffline.value = false
    getCharacters(characterIds)
  }

  private fun getCharacters(characterIds: List<Int>) {
    if (!statusProvider.isOnline()) _isOffline.value = true

    viewModelScope.launch {
      when (val result = repository.getCharactersByIds(characterIds)) {
        is QueryResult.Successful -> _characters.value = result.data.toViewCharacterItems()
        QueryResult.NoCache -> _isNoCache.value = true
        QueryResult.Error -> _onError.value = true
      }
      _loadingState.value = LoadingState.None
    }
  }

  private fun List<ModelCharacter>.toViewCharacterItems(): MutableList<ViewCharacterItem> =
    map { character ->
      character.toViewCharacterItem(
        onClick = {
          _selectedCharacterId.trySend(character.id)
        },
      )
    }.toMutableList()
}