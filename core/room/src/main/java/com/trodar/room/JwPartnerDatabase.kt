package com.trodar.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trodar.common.Core
import com.trodar.room.convertes.DateConverter
import com.trodar.room.notepad.NotepadDao
import com.trodar.room.notepad.NotepadDbEntity
import com.trodar.room.preach.PreachDao
import com.trodar.room.preach.PreachDbEntity
import com.trodar.room.simplepreach.SimplePreachDao
import com.trodar.room.simplepreach.SimplePreachDbEntity

@Database(
    entities = [PreachDbEntity::class, SimplePreachDbEntity::class, NotepadDbEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class JwPartnerDatabase : RoomDatabase() {
    abstract fun preachDao(): PreachDao

    abstract fun simplePreachDao(): SimplePreachDao

    abstract fun notepadDao(): NotepadDao
}