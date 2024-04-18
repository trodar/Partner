package com.trodar.room.notepad

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trodar.common.Core
import com.trodar.room.model.Notepad
import java.util.Date

@Entity(tableName = Core.databaseConst.NOTEPAD_TABLE_NAME)
class NotepadDbEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val txt: String,
    override val date: Date) : Notepad