package com.metis.rickmorty.ui.episode

data class ViewEpisodeItem(
    val name: String,
    val airDate: String,
    val episode: String,
    val onClick: () -> Unit,
) {
    fun onClick() = onClick.invoke()
}
