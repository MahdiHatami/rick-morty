package com.metis.rickmorty.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiOrigin(

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "url")
    val url: String,
)
