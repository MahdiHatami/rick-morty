package com.metis.rickmorty.ui.character

data class ViewCharacterItem(
    val name: String,
    val imageUrl: String,
    val status: String,
    val onClick: () -> Unit
) {
    fun onClick() = onClick.invoke()
}
