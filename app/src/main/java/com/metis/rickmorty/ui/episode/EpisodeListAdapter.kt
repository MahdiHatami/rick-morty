package com.metis.rickmorty.ui.episode

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metis.rickmorty.databinding.ItemEpisodeBinding

class EpisodeListAdapter : RecyclerView.Adapter<EpisodeViewHolder>() {
    private var episods: MutableList<ViewEpisodeItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setEpisodes(episodes: List<ViewEpisodeItem>) {
        this.episods = episodes.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode: ViewEpisodeItem = episods[position]
        holder.setEpisode(episode)
    }

    override fun getItemCount(): Int {
        return episods.size
    }
}
