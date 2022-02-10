package com.metis.rickmorty.factory

import com.metis.rickmorty.data.source.local.entity.DbLocation

object LocationDataFactory {

  internal fun makeDbLocation(): DbLocation =
    DbLocation(
      name = DataFactory.randomString(),
      url = DataFactory.randomString()
    )
}