package com.metis.rickmorty.factory

import com.metis.rickmorty.data.source.local.entity.DbOrigin
import com.metis.rickmorty.domain.model.ModelOrigin

object OriginDataFactory {
    internal fun makeDbOrigin(): DbOrigin =
        DbOrigin(
            name = DataFactory.randomString(),
            url = DataFactory.randomString()
        )

    internal fun makeModelOrigin(): ModelOrigin =
        ModelOrigin(
            name = DataFactory.randomString(),
            url = DataFactory.randomString()
        )
}
