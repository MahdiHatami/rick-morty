package com.metis.rickmorty.ui.episode

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun EpisodeListScreen(viewModel: EpisodeListViewModel) {
  val uiState = viewModel.uiState.collectAsState()
  SwipeRefresh(
    state = rememberSwipeRefreshState(uiState.value.isRefreshing),
    onRefresh = { viewModel.loadEpisodes() }
  ) {
    EpisodeList(episodes = uiState.value.episodes)
  }
}

@Composable
fun EpisodeList(episodes: List<ViewEpisodeItem>) {
  LazyColumn {
    items(episodes) { message ->
      EpisodeListItem(message)
    }
  }
}

@Preview
@Composable
fun PreviewEpisodesList() {
  EpisodeList(episodes = listOf(
    ViewEpisodeItem(
      name = "Pilot",
      episode = "S01E01",
      airDate = "December 2 1990",
      onClick = {}
    ),
    ViewEpisodeItem(
      name = "Flying",
      episode = "S01E02",
      airDate = "September 2 2001",
      onClick = {}
    ),
  )
  )
}