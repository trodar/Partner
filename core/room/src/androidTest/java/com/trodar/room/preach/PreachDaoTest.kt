package com.trodar.room.preach

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trodar.room.DataBaseTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class PreachDaoTest : DataBaseTest() {

    private lateinit var preachDao: PreachDao
    private lateinit var preachList: List<PreachDbEntity>


    @Before
    fun setUp() {
        preachDao = db.preachDao()
        preachList = getListPreachDbEntity()
        runBlocking {
            preachList.forEach {
                preachDao.insertPreach(it)
            }
        }
    }

    @Test
    fun getListPreachTest() = runBlocking {

        val preachListCheck = preachDao.getAllData().first()

        assertEquals(preachListCheck, preachList)
        assertEquals(preachListCheck.size, 7)
    }

    @Test
    fun getYearPeriodListTest() = runBlocking {
        val yearList = preachDao.getYearPeriodList("202209", "202309").first()
        assertEquals(yearList.hours, 68)
        val yearList2 = preachDao.getYearPeriodList("202109", "202209").first()
        assertEquals(yearList2.hours, 34)
    }

    @Test
    fun getYearMonthListTest() = runBlocking {
        val yearList = preachDao.getMonthsPeriodList("202309", "202409").first()
        assertEquals(yearList.size, 2)
        val yearList2 = preachDao.getMonthsPeriodList("202109", "202209").first()
        assertEquals(yearList2.size, 1)
    }

    @Test
    fun getYearPreachesOfMonthTest() = runBlocking {
        val preachesList = preachDao.getPreachesOfMonth("202310").first()
        assertEquals(preachesList.size, 2)
        val preachesList2 = preachDao.getPreachesOfMonth("202101").first()
        assertEquals(preachesList2.size, 1)
    }

    @Test
    fun getPreachFromIdTest() = runBlocking {
        val preachDb1 = preachDao.getPreach(2).first()
        val preachDb2 = preachDao.getPreach(8).first()
        val preachLocal1 = getListPreachDbEntity()[1]
        val preachLocal2 = getListPreachDbEntity()[4]

        assertEquals(preachDb1, preachLocal1)
        assertNotEquals(preachDb1, preachLocal2)

        assertNull(preachDb2)
    }

    @Test
    fun getMinDateTest() = runBlocking {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val minDate = preachDao.getMinDate()

        assertNotNull(minDate)
        assertEquals(formatter.parse("2021-01-06"), minDate)
    }


    @Test
    fun deletePreachTest() = runBlocking {

        val preach = preachList[6]
        preachDao.deletePreach(preach)

        val preachList = preachDao.getAllData().first()
        val checkPreach = preachList.filter {
            it.id == 7
        }

        assert(checkPreach.isEmpty())
        assertEquals(preachList.size, 6)
    }

    @Test
    fun deleteAllAndInsertAllPreachesTest() = runBlocking {
        preachDao.deleteAll()

        var preaches = preachDao.getAllData().first()
        assertEquals(preaches.size, 0)

        preachDao.insertPreaches(getListPreachDbEntity())

        preaches = preachDao.getAllData().first()
        assertEquals(preaches, preachList)
    }
}

fun getListPreachDbEntity(): List<PreachDbEntity> {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return listOf(
        PreachDbEntity(
            id = 1,
            time = 11,
            publication = 1,
            video = 2,
            returns = 0,
            study = 0,
            description = "test database list 1",
            formatter.parse("2021-01-06")!!
        ),
        PreachDbEntity(
            id = 2,
            time = 34,
            publication = 5,
            video = 0,
            returns = 3,
            study = 2,
            description = "test database list2",
            formatter.parse("2022-01-06")!!
        ),
        PreachDbEntity(
            id = 3,
            time = 34,
            publication = 5,
            video = 0,
            returns = 3,
            study = 2,
            description = "test database list2",
            formatter.parse("2023-01-06")!!
        ),
        PreachDbEntity(
            id = 4,
            time = 34,
            publication = 5,
            video = 0,
            returns = 3,
            study = 2,
            description = "test database list2",
            formatter.parse("2023-08-06")!!
        ),
        PreachDbEntity(
            id = 5,
            time = 34,
            publication = 5,
            video = 0,
            returns = 3,
            study = 2,
            description = "test database list2",
            formatter.parse("2023-10-06")!!
        ),
        PreachDbEntity(
            id = 6,
            time = 40,
            publication = 5,
            video = 0,
            returns = 3,
            study = 2,
            description = "test database list2",
            formatter.parse("2023-10-06")!!
        ),
        PreachDbEntity(
            id = 7,
            time = 34,
            publication = 5,
            video = 0,
            returns = 3,
            study = 2,
            description = "test database list2",
            formatter.parse("2024-03-06")!!
        ),
    )
}