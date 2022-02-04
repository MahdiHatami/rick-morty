package com.metis.rickmorty.ui.mapper

import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.ui.episode.ViewEpisodeItem

fun ModelEpisode.toViewEpisodeItem(onClick: () -> Unit) = ViewEpisodeItem(
    name = name,
    airDate = airDate,
    episode = episode,
    onClick = onClick,
)
