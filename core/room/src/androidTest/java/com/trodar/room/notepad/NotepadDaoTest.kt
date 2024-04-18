package com.trodar.room.notepad

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trodar.room.DataBaseTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class NotepadDaoTest : DataBaseTest() {
    private lateinit var notepadDao: NotepadDao
    private lateinit var notepadList: List<NotepadDbEntity>

    @Before
    fun setUp() {
        notepadDao = db.notepadDao()
        notepadList = getListNotepadDbEntity()
        runBlocking {
            notepadList.forEach {
                notepadDao.editNotepad(it)
            }
        }
    }

    @Test
    fun getListTest() = runBlocking {
        val list = notepadDao.getList().first()

        assertEquals(list.size, 3)
    }

    @Test
    fun getItemTest() = runBlocking {
        val itemDb = notepadDao.getItem(2).first()
        val itemLocal = notepadList[1]

        assertEquals(itemDb?.id, itemLocal.id)
    }

    @Test
    fun deleteNotepadTest() = runBlocking {
        val itemLocal = notepadList[0]

        notepadDao.deleteNotepad(itemLocal)

        val list = notepadDao.getList().first()
        val itemDb = notepadDao.getItem(1).first()

        assertNull(itemDb)
        assertEquals(list.size, 2)
    }

    @Test
    fun deleteAllInsertListTest() = runBlocking {
        notepadDao.deleteAll()

        var list = notepadDao.getList().first()

        assert(list.isEmpty())

        notepadDao.insertNotepads(notepadList)
        list = notepadDao.getList().first()

        assertEquals(list.size, 3)
        assertEquals(list[1].id, list[1].id)
    }
}

fun getListNotepadDbEntity(): List<NotepadDbEntity> {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return listOf(
        NotepadDbEntity(
            id = 1,
            txt = "Test",
            date = formatter.parse("2021-01-06")!!
        ),
        NotepadDbEntity(
            id = 2,
            txt = "Test \ntest2",
            date = formatter.parse("2022-01-06")!!
        ),
        NotepadDbEntity(
            id = 3,
            txt = "Test",
            date = formatter.parse("2023-01-06")!!
        ),
    )
}