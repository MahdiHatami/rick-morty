package com.metis.rickmorty.factory

import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.domain.model.ModelCharacter
import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult
import com.metis.rickmorty.domain.model.QueryResult
import com.metis.rickmorty.mapper.toModelCharacter
import com.metis.rickmorty.ui.character.ViewCharacterItem
import com.metis.rickmorty.ui.episode.ViewEpisodeItem
import com.metis.rickmorty.ui.mapper.toViewCharacterItem
import com.metis.rickmorty.ui.mapper.toViewEpisodeItem

internal class FakeRepository : Repository {
    companion object {
        val fakeEpisodes = EpisodeDataFactory.makeEpisodes(6)
        val fakeEpisodesView = fakeEpisodes.map {
            it.toViewEpisodeItem { }
        }
        val fakeCharaters = CharacterDataFactory.makeModelCharacters(6)
        val fakeCharatersView = fakeCharaters.map {
            it.toViewCharacterItem { }
        }
    }

    fun getViewCharacters(): List<ViewCharacterItem> {
        return fakeCharatersView
    }

    fun getViewEpisodes(): List<ViewEpisodeItem> {
        return fakeEpisodesView
    }

    override suspend fun getEpisodes(page: Int): PageQueryResult<List<ModelEpisode>> {
        return PageQueryResult.Successful(fakeEpisodes)
    }

    override suspend fun getCharactersByIds(characterIds: List<Int>): QueryResult<List<ModelCharacter>> {
        return QueryResult.Successful(fakeCharaters)
    }

    override suspend fun getCharacterDetails(characterId: Int): QueryResult<ModelCharacter?> {
        return QueryResult.Successful(
            CharacterDataFactory.makeDbCharacter().toModelCharacter()
        )
    }
}
