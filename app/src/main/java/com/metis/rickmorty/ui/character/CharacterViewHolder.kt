package com.metis.rickmorty.ui.character

import androidx.recyclerview.widget.RecyclerView
import com.metis.rickmorty.databinding.CharacterListItemBinding

class CharacterViewHolder internal constructor(internal val binding: CharacterListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setCharacter(character: ViewCharacterItem) {
        binding.character = character
    }
}
