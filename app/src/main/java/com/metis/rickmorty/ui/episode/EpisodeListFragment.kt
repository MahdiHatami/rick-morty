package com.metis.rickmorty.ui.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.metis.rickmorty.ui.BaseFragment
import com.metis.rickmorty.ui.util.EndlessRecyclerViewScrollListener
import com.metis.rickmorty.ui.util.launchWhileResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodeListFragment : BaseFragment() {

  private val viewModel by viewModels<EpisodeListViewModel> { viewModelFactoryProvider }

  private var endlessScrollListener: EndlessRecyclerViewScrollListener? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(requireContext()).apply {
      setContent {
        EpisodeListScreen(viewModel = viewModel)
      }
    }
  }

  @Composable
  fun EpisodeListScreen(viewModel: EpisodeListViewModel) {
    val uiState = viewModel.uiState
    SwipeRefresh(
      state = rememberSwipeRefreshState(uiState.value.isRefreshing),
      onRefresh = { viewModel.loadEpisodes() }
    ) {
      LazyColumn {
        items(uiState.value.episodes) { message ->
          EpisodeListItem(message)
        }
      }
    }
  }

  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  fun EpisodeListItem(episode: ViewEpisodeItem) {
    Card(
      onClick = { episode.onClick() },
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .fillMaxWidth(),
      elevation = 2.dp,
      backgroundColor = Color.White,
      shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
      Row {
        Column(
          modifier = Modifier.padding(6.dp)
        ) {
          Text(text = episode.episode, style = typography.h6)
          Text(
            text = episode.name,
            style = typography.caption,
          )
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val linearLayoutManager = LinearLayoutManager(context)
    // binding.episodeList.layoutManager = linearLayoutManager
    endlessScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
      override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
        viewModel.loadMoreEpisodes(page = page + 1)
      }
    }.also {
      // binding.episodeList.addOnScrollListener(it)
    }

    lifecycle.launchWhileResumed {
      // viewModel.onError.collectLatest {
      //   if (it) showErrorMessage()
      // }
    }
    //
    // lifecycle.launchWhileResumed {
    //   viewModel.isOffline.collectLatest {
    //     if (it) showOfflineMessage()
    //   }
    // }
    //
    // lifecycle.launchWhileResumed {
    //   viewModel.isEndOfList.collectLatest {
    //     if (it) reachedEndOfList()
    //   }
    // }

    lifecycle.launchWhileResumed {
      viewModel.selectedEpisodeCharacterIds.collectLatest { characterIds ->
        onEpisodeRowClick(characterIds)
      }
    }
  }

  private fun onEpisodeRowClick(characterIds: IntArray) {
    val action = EpisodeListFragmentDirections
      .actionEpisodeListFragmentToCharacterListFragment(characterIds)
    view?.findNavController()?.navigate(action)
  }

  private fun reachedEndOfList() {
    // binding.episodeListProgress.visibility = View.GONE
  }

  override fun onDestroyView() {
    super.onDestroyView()
    endlessScrollListener = null
  }
}