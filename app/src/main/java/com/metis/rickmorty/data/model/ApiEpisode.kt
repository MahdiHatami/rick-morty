package com.metis.rickmorty.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiEpisode(

    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "air_date")
    val airDate: String,

    @SerialName(value = "episode")
    val episode: String,

    @SerialName(value = "characters")
    val characters: List<String>,

    @SerialName(value = "url")
    val url: String,

    @SerialName(value = "created")
    val created: String,
)
