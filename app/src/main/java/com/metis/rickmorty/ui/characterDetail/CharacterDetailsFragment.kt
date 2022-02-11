package com.metis.rickmorty.ui.characterDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.metis.rickmorty.R
import com.metis.rickmorty.databinding.CharacterDetailsFragmentBinding
import com.metis.rickmorty.databinding.CharacterListFragmentBinding
import com.metis.rickmorty.ui.BaseFragment
import com.metis.rickmorty.ui.character.CharacterListFragmentArgs
import com.metis.rickmorty.ui.character.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : BaseFragment() {


  private lateinit var binding: CharacterDetailsFragmentBinding

  // private val args: CharacterDetailsFragmentArgs by navArgs()

  private val viewModel by viewModels<CharacterDetailsViewModel> { viewModelFactoryProvider }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.character_details_fragment, container, false)
  }

}