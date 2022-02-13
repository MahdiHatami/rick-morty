package com.metis.rickmorty.ui.characterDetail

import androidx.lifecycle.ViewModel
import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.utils.StatusProvider
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
  private val repository: Repository,
  private val statusProvider: StatusProvider
) : ViewModel() {
  // TODO: Implement the ViewModel
}