package com.metis.rickmorty.ui.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.metis.rickmorty.ui.BaseFragment
import com.metis.rickmorty.ui.util.launchWhileResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodeListFragment : BaseFragment() {

  private val viewModel by viewModels<EpisodeListViewModel> { viewModelFactoryProvider }

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

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

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
}