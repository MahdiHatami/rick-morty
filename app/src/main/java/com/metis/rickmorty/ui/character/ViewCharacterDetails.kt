package com.metis.rickmorty.ui.character

data class ViewCharacterDetails(
  val id: Int,
  val name: String,
  val status: String,
  val species: String,
  val gender: String,
  val origin: String,
  val location: String,
  val imageUrl: String,
  val created: String,
  val isKilledByUser: Boolean,
)

