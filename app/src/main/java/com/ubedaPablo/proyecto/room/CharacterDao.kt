package com.ubedaPablo.proyecto.room

import androidx.room.*

@Dao
interface CharacterDao {
    @Insert
    suspend fun insertAll(vararg characters: CharacterDnD)

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getOne(id: Int): CharacterDnD

    @Query("SELECT * FROM characters")
    suspend fun getAll(): List<CharacterDnD>

    @Update
    suspend fun updateAll(vararg characters: CharacterDnD)

    @Delete
    suspend fun delete(character: CharacterDnD)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}