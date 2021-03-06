package com.metis.rickmorty.domain.model

data class ModelCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val isAlive: Boolean,
    val species: String,
    val type: String,
    val gender: String,
    val origin: ModelOrigin,
    val location: ModelLocation,
    val imageUrl: String,
    val url: String,
    val created: String,
    val isKilledByUser: Boolean,
)
