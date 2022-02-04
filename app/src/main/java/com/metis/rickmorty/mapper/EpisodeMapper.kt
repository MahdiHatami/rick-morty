package com.metis.rickmorty.mapper

import com.metis.rickmorty.data.model.ApiEpisode
import com.metis.rickmorty.data.source.local.entity.DbEpisode
import com.metis.rickmorty.domain.model.ModelEpisode

private const val SEPARATOR: Char = '/'

internal fun extractCharacterIds(characters: List<String>): List<Int> {
    return characters.map { it.split(SEPARATOR).last().toInt() }
}

internal fun ApiEpisode.toDbEpisode() =
    DbEpisode(
        id = id,
        name = name,
        airDate = "",
        episode = episode,
        characterIds = extractCharacterIds(characters),
        url = url,
        created = created,
    )

internal fun DbEpisode.toModelEpisode() =
    ModelEpisode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characterIds = characterIds,
        url = url,
        created = created,
    )
