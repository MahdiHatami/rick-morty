package com.metis.rickmorty.factory

import com.metis.rickmorty.data.source.local.entity.DbLocation
import com.metis.rickmorty.domain.model.ModelLocation

object LocationDataFactory {

    internal fun makeDbLocation(): DbLocation =
        DbLocation(
            name = DataFactory.randomString(),
            url = DataFactory.randomString()
        )

    internal fun makeModelLocation(): ModelLocation =
        ModelLocation(
            name = DataFactory.randomString(),
            url = DataFactory.randomString()
        )
}
