package com.metis.rickmorty.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.metis.rickmorty.data.source.local.entity.DbCharacter

@Dao
interface CharacterDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCharacter(entityCharacter: DbCharacter)

  @Query(value = "SELECT * FROM characters WHERE _id IN (:characterIds)")
  suspend fun queryCharactersByIds(characterIds: List<Int>): List<DbCharacter>
}