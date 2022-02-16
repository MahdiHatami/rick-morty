package com.metis.rickmorty.ui.episode

data class EpisodeListUiState(
  val loading: Boolean = false,
  val loadingMore: Boolean = false,
  val isOffline: Boolean = false,
  val isEndOfList: Boolean = false,
  val onError: Boolean = false,
  val episodes: List<ViewEpisodeItem> = emptyList()
)