package com.metis.rickmorty.ui.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.metis.rickmorty.R
import com.metis.rickmorty.ui.character.CharacterListAdapter
import com.metis.rickmorty.ui.character.ViewCharacterItem
import com.metis.rickmorty.ui.episode.EpisodeListAdapter
import com.metis.rickmorty.ui.episode.ViewEpisodeItem
import com.metis.rickmorty.utils.LoadingState
import com.squareup.picasso.Picasso

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("onRefreshListener")
fun SwipeRefreshLayout.onRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener?) {
    setOnRefreshListener(listener)
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.setIsRefreshing(loadingState: LoadingState?) {
    isRefreshing = loadingState == LoadingState.Loading
}

@BindingAdapter("isLoading")
fun ProgressBar.setIsLoading(loadingState: LoadingState?) {
    visibility = if (loadingState == LoadingState.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("isLoadingMore")
fun ProgressBar.setIsLoadingMore(loadingState: LoadingState?) {
    visibility = if (loadingState == LoadingState.LoadingMore) View.VISIBLE else View.GONE
}

@BindingAdapter("episodes")
fun RecyclerView.setEpisodes(itemViewModels: List<ViewEpisodeItem>?) {
    val adapter = getOrCreateEpisodeListAdapter()
    adapter.setEpisodes(itemViewModels ?: emptyList())
}

private fun RecyclerView.getOrCreateEpisodeListAdapter(): EpisodeListAdapter {
    return if (adapter != null && adapter is EpisodeListAdapter) {
        adapter as EpisodeListAdapter
    } else {
        val newAdapter = EpisodeListAdapter()
        adapter = newAdapter
        newAdapter
    }
}

@BindingAdapter("characters")
fun RecyclerView.setCharacters(itemViewModels: List<ViewCharacterItem>?) {
    val adapter = getOrCreateCharacterListAdapter()
    adapter.setCharacters(itemViewModels ?: emptyList())
}

private fun RecyclerView.getOrCreateCharacterListAdapter(): CharacterListAdapter {
    return if (adapter != null && adapter is CharacterListAdapter) {
        adapter as CharacterListAdapter
    } else {
        val newAdapter = CharacterListAdapter()
        adapter = newAdapter
        newAdapter
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.ic_placeholder)
        .into(this)
}
