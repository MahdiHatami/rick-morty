package com.metis.rickmorty.ui.mapper

import com.metis.rickmorty.domain.model.ModelCharacter
import com.metis.rickmorty.ui.character.ViewCharacterItem
import com.metis.rickmorty.ui.characterDetail.ViewCharacterDetails

fun ModelCharacter.toViewCharacterItem(onClick: () -> Unit) = ViewCharacterItem(
    name = name,
    imageUrl = imageUrl,
    status = status,
    onClick = onClick,
)

fun ModelCharacter.toViewCharacterDetails() = ViewCharacterDetails(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    origin = origin.name,
    location = location.name,
    imageUrl = imageUrl,
    created = created,
    isKilledByUser = isKilledByUser,
)
