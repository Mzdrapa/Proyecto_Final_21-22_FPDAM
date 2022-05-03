package com.ubedaPablo.proyecto.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAll(): List<CharacterDnD>

    @Insert
    fun insertAll(vararg characters: CharacterDnD)

    @Delete
    fun delete(character: CharacterDnD)

    @Query("DELETE FROM characters")
    fun deleteAll()
}