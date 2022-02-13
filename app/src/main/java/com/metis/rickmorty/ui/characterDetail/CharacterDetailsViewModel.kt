package com.metis.rickmorty.ui.characterDetail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.domain.model.QueryResult
import com.metis.rickmorty.ui.mapper.toViewCharacterDetails
import com.metis.rickmorty.utils.LoadingState
import com.metis.rickmorty.utils.StatusProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
  private val repository: Repository,
  private val statusProvider: StatusProvider
) : ViewModel() {

  private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.None)
  val loadingState: StateFlow<LoadingState> = _loadingState

  private val _isOffline: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val isOffline: StateFlow<Boolean> = _isOffline

  private val _isNoCache: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val isNoCache: StateFlow<Boolean> = _isNoCache

  private val _onError: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val onError: StateFlow<Boolean> = _onError

  private val _character: MutableStateFlow<ViewCharacterDetails?> = MutableStateFlow(null)
  val character: StateFlow<ViewCharacterDetails?> = _character

  fun loadCharacterDetails(characterId: Int) {
    _loadingState.value = LoadingState.Loading
    _onError.value = false
    _isOffline.value = false
    getCharacterDetails(characterId)
  }

  private fun getCharacterDetails(characterId: Int) {
    viewModelScope.launch {
      when (val result = repository.getCharacterDetails(characterId)) {
        is QueryResult.Successful -> _character.value = result.data?.toViewCharacterDetails()
        QueryResult.NoCache -> _isNoCache.value = true
        QueryResult.Error -> _onError.value = true
      }
      _loadingState.value = LoadingState.None
    }
  }
}