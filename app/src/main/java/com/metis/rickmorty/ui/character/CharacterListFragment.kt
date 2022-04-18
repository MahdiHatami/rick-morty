package com.metis.rickmorty.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.metis.rickmorty.databinding.CharacterListFragmentBinding
import com.metis.rickmorty.ui.BaseFragment
import com.metis.rickmorty.ui.util.launchWhileResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharacterListFragment : BaseFragment() {

    private lateinit var binding: CharacterListFragmentBinding

    private val args: CharacterListFragmentArgs by navArgs()

    private val viewModel: CharacterListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadCharacters(args.characterIds.toList())

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

        lifecycle.launchWhileResumed {
            viewModel.selectedCharacterId.collectLatest { characterId ->
                onCharacterRowClick(characterId)
            }
        }
    }

    private fun onCharacterRowClick(characterId: Int) {
        val action = CharacterListFragmentDirections
            .actionCharacterListFragmentToCharacterDetails(characterId)
        view?.findNavController()?.navigate(action)
    }
}
