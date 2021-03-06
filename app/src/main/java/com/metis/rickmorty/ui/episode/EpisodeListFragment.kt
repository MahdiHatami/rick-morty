package com.metis.rickmorty.ui.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.metis.rickmorty.databinding.EpisodeListFragmentBinding
import com.metis.rickmorty.ui.BaseFragment
import com.metis.rickmorty.ui.util.EndlessRecyclerViewScrollListener
import com.metis.rickmorty.ui.util.launchWhileResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodeListFragment : BaseFragment() {

    private lateinit var binding: EpisodeListFragmentBinding

    private val viewModel: EpisodeListViewModel by viewModels()

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

    // make sure that view is fully created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)
        binding.episodeList.layoutManager = linearLayoutManager
        endlessScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
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
        val action = EpisodeListFragmentDirections
            .actionEpisodeListFragmentToCharacterListFragment(characterIds)
        view?.findNavController()?.navigate(action)
    }

    private fun reachedEndOfList() {
        binding.episodeListProgress.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        endlessScrollListener = null
    }
}
