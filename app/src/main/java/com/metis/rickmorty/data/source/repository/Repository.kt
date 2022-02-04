package com.metis.rickmorty.data.source.repository

import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult

interface Repository {
  suspend fun getEpisodes(page: Int): PageQueryResult<List<ModelEpisode>>
}
