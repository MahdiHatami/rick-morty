package com.metis.rickmorty.utils

sealed interface LoadingState {
    object None : LoadingState
    object Loading : LoadingState
    object LoadingMore : LoadingState
}
