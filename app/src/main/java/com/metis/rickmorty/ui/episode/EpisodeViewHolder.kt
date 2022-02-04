package com.metis.rickmorty.ui.episode

import androidx.recyclerview.widget.RecyclerView
import com.metis.rickmorty.databinding.ItemEpisodeBinding

class EpisodeViewHolder internal constructor(internal val binding: ItemEpisodeBinding) :
  RecyclerView.ViewHolder(binding.root) {

  fun setEpisode(episode: ViewEpisodeItem) {
    binding.episode = episode
  }
}
