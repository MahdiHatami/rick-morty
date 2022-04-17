package com.metis.rickmorty.ui.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.metis.rickmorty.databinding.CharacterDetailsFragmentBinding
import com.metis.rickmorty.ui.BaseFragment
import com.metis.rickmorty.ui.util.launchWhileResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharacterDetailsFragment : BaseFragment() {

  private lateinit var binding: CharacterDetailsFragmentBinding

  private val args: CharacterDetailsFragmentArgs by navArgs()

  private val viewModel: CharacterDetailsViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = CharacterDetailsFragmentBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.loadCharacterDetails(args.characterId)

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
      viewModel.isNoCache.collectLatest {
        if (it) onNoOfflineData()
      }
    }
  }
}