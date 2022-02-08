package com.metis.rickmorty.mapper

import com.metis.rickmorty.data.model.ApiCharacter
import com.metis.rickmorty.data.model.ApiLocation
import com.metis.rickmorty.data.model.ApiOrigin
import com.metis.rickmorty.data.source.local.entity.DbCharacter
import com.metis.rickmorty.data.source.local.entity.DbLocation
import com.metis.rickmorty.data.source.local.entity.DbOrigin
import com.metis.rickmorty.domain.model.ModelCharacter
import com.metis.rickmorty.domain.model.ModelLocation
import com.metis.rickmorty.domain.model.ModelOrigin

private const val DEAD: String = "dead"
private const val SEPARATOR: Char = '/'

internal fun extractEpisodeIds(episodes: List<String>): List<Int> {
  return episodes.map { it.split(SEPARATOR).last().toInt() }
}

internal fun ApiCharacter.toDbCharacter() =
  DbCharacter(
    id = id,
    name = name,
    status = status,
    isAlive = !status.equals(DEAD, ignoreCase = true),
    species = species,
    type = type,
    gender = gender,
    origin = origin.toDbOrigin(),
    location = location.toDbLocation(),
    image = image,
    episodeIds = extractEpisodeIds(episodes),
    url = url,
    created = created,
    isKilledByUser = false,
  )

private fun ApiOrigin.toDbOrigin() =
  DbOrigin(
    name = name,
    url = url,
  )

private fun ApiLocation.toDbLocation() =
  DbLocation(
    name = name,
    url = url,
  )

internal fun DbCharacter.toModelCharacter() =
  ModelCharacter(
    id = id,
    name = name,
    status = status,
    isAlive = isAlive,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toModelOrigin(),
    location = location.toModelLocation(),
    imageUrl = image,
    episodeIds = episodeIds,
    url = url,
    created = created,
    isKilledByUser = isKilledByUser,
  )
private fun DbOrigin.toModelOrigin() =
  ModelOrigin(
    name = name,
    url = url,
  )

private fun DbLocation.toModelLocation() =
  ModelLocation(
    name = name,
    url = url,
  )