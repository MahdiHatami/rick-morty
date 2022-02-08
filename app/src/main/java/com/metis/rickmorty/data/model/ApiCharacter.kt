package com.metis.rickmorty.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCharacter(

    @SerializedName(value = "id")
    val id: Int,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "status")
    val status: String,

    @SerialName(value = "species")
    val species: String,

    @SerialName(value = "type")
    val type: String,

    @SerialName(value = "gender")
    val gender: String,

    @SerialName(value = "origin")
    val origin: ApiOrigin,

    @SerialName(value = "location")
    val location: ApiLocation,

    @SerialName(value = "image")
    val image: String,

    @SerialName(value = "episode")
    val episodes: List<String>,

    @SerialName(value = "url")
    val url: String,

    @SerialName(value = "created")
    val created: String,
)
