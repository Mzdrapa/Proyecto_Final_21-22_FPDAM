package com.ubedaPablo.proyecto.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAll(): List<CharacterDYD>

    @Insert
    fun insertAll(vararg characters: CharacterDYD)

    @Delete
    fun delete(character: CharacterDYD)

    @Query("DELETE FROM characters")
    fun deleteAll()
}