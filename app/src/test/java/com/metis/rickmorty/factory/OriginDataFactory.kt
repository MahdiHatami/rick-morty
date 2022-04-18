package com.metis.rickmorty.factory

import com.metis.rickmorty.data.source.local.entity.DbOrigin

object OriginDataFactory {
    internal fun makeDbOrigin(): DbOrigin =
        DbOrigin(
            name = DataFactory.randomString(),
            url = DataFactory.randomString()
        )
}
