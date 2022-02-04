package com.metis.rickmorty.ui.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.metis.rickmorty.R
import com.metis.rickmorty.databinding.EpisodeListFragmentBinding
import com.metis.rickmorty.ui.BaseFragment
import com.metis.rickmorty.ui.util.launchWhileResumed
import com.metis.rickmorty.ui.util.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodeListFragment : BaseFragment() {

  private lateinit var binding: EpisodeListFragmentBinding

  private val viewModel by viewModels<EpisodeListViewModel> { viewModelFactoryProvider }

  private var endlessScrollListener: EndlessRecyclerViewScrollListener? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = EpisodeListFragmentBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val linearLayoutManager = LinearLayoutManager(context)
    binding.episodeList.layoutManager = linearLayoutManager
    endlessScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
      override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
        viewModel.loadMoreEpisodes(page = page + 1)
      }
    }.also {
      binding.episodeList.addOnScrollListener(it)
    }

    lifecycle.launchWhileResumed {
      viewModel.onError.collectLatest {
        if (it) showErrorMessage()
      }
    }

    lifecycle.launchWhileResumed {
      viewModel.isOffline.collectLatest {
        if (it) showOfflineMessage()
      }
    }

    lifecycle.launchWhileResumed {
      viewModel.isEndOfList.collectLatest {
        if (it) reachedEndOfList()
      }
    }


    lifecycle.launchWhileResumed {
      viewModel.selectedEpisodeCharacterIds.collectLatest { characterIds ->
        onEpisodeRowClick(characterIds)
      }
    }
  }

  private fun onEpisodeRowClick(characterIds: IntArray) {
    // val action = EpisodeListFragmentDirections
    //   .actionEpisodeListFragmentToCharacterListFragment(characterIds)
    // view?.findNavController()?.navigate(action)
  }

  private fun showErrorMessage() {
    Toast.makeText(context, R.string.error_message, Toast.LENGTH_LONG).show()
  }

  private fun showOfflineMessage() {
    Toast.makeText(context, R.string.offline_app, Toast.LENGTH_SHORT).show()
  }

  private fun reachedEndOfList() {
    binding.episodeListProgress.visibility = View.GONE
  }

  override fun onDestroyView() {
    super.onDestroyView()
    endlessScrollListener = null
    // binding = null // why
  }
}