package com.trodar.room.notepad

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trodar.common.Core
import kotlinx.coroutines.flow.Flow

@Dao
interface NotepadDao {
    @Query("SELECT * FROM ${Core.databaseConst.NOTEPAD_TABLE_NAME}")
    fun getList(): Flow<List<NotepadDbEntity>>

    @Query("SELECT * FROM ${Core.databaseConst.NOTEPAD_TABLE_NAME} WHERE id= :id")
    fun getItem(id: Int): Flow<NotepadDbEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editNotepad(notepadDbEntity: NotepadDbEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotepads(notepads: List<NotepadDbEntity>)

    @Delete
    suspend fun deleteNotepad(notepadDbEntity: NotepadDbEntity)

    @Query("DELETE FROM ${Core.databaseConst.NOTEPAD_TABLE_NAME}")
    suspend fun deleteAll()
}