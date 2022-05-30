package com.ubedaPablo.proyecto.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    suspend fun getAll(): List<CharacterDnD>

    @Insert
    suspend fun insertAll(vararg characters: CharacterDnD)

    @Delete
    suspend fun delete(character: CharacterDnD)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}