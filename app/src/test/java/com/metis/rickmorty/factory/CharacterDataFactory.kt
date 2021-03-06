package com.metis.rickmorty.factory

import com.metis.rickmorty.data.source.local.entity.DbCharacter
import com.metis.rickmorty.domain.model.ModelCharacter

object CharacterDataFactory {

    internal fun makeDbCharacter(
        characterId: Int = DataFactory.randomInt(),
        status: String = DataFactory.randomString(),
        isAlive: Boolean = DataFactory.randomBoolean(),
        isKilledByUser: Boolean = false,
    ): DbCharacter =
        DbCharacter(
            id = characterId,
            name = DataFactory.randomString(),
            status = status,
            isAlive = isAlive,
            species = DataFactory.randomString(),
            type = DataFactory.randomString(),
            gender = DataFactory.randomString(),
            origin = OriginDataFactory.makeDbOrigin(),
            location = LocationDataFactory.makeDbLocation(),
            image = DataFactory.randomString(),
            url = DataFactory.randomString(),
            created = DataFactory.randomString(),
            isKilledByUser = isKilledByUser,
        )

    internal fun makeModelCharacter(
        characterId: Int = DataFactory.randomInt(),
        status: String = DataFactory.randomString(),
        isAlive: Boolean = DataFactory.randomBoolean(),
        isKilledByUser: Boolean = false,
    ): ModelCharacter =
        ModelCharacter(
            id = characterId,
            name = DataFactory.randomString(),
            status = status,
            isAlive = isAlive,
            species = DataFactory.randomString(),
            type = DataFactory.randomString(),
            gender = DataFactory.randomString(),
            origin = OriginDataFactory.makeModelOrigin(),
            location = LocationDataFactory.makeModelLocation(),
            imageUrl = DataFactory.randomString(),
            url = DataFactory.randomString(),
            created = DataFactory.randomString(),
            isKilledByUser = isKilledByUser,
        )

    internal fun makeDbCharacters(count: Int): List<DbCharacter> {
        val charatersList: MutableList<DbCharacter> = ArrayList()
        repeat(count) {
            charatersList.add(makeDbCharacter())
        }
        return charatersList
    }
    internal fun makeModelCharacters(count: Int): List<ModelCharacter> {
        val charatersList: MutableList<ModelCharacter> = ArrayList()
        repeat(count) {
            charatersList.add(makeModelCharacter())
        }
        return charatersList
    }
}
