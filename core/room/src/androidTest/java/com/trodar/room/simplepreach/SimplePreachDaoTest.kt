package com.trodar.room.simplepreach

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trodar.room.DataBaseTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class SimplePreachDaoTest : DataBaseTest() {
    private lateinit var simplePreachDao: SimplePreachDao
    private lateinit var simplePreachList: List<SimplePreachDbEntity>


    @Before
    fun setUp() {
        simplePreachDao = db.simplePreachDao()
        simplePreachList = getListSimplePreachDbEntity()
        runBlocking {
            simplePreachList.forEach {
                simplePreachDao.insertSimplePreach(it)
            }
        }
    }

    @Test
    fun getAllDataTest() = runBlocking {
        val list = simplePreachDao.getAllData()?.first()

        assertNotNull(list)
        assertEquals(list, simplePreachList)
    }

    @Test
    fun getSimplePreachItemTest() = runBlocking {
        val itemDb = simplePreachDao.getSimplePreachItem("202104").first()

        val itemLocal = simplePreachList[3]

        assertNotNull(itemDb)
        assertEquals(itemDb, itemLocal)
    }

    @Test
    fun getMonthPeriodListTest() = runBlocking {
        val list = simplePreachDao.getMonthsPeriodList("202102", "202105")?.first()

        val itemLocal = simplePreachList[4]

        assertNotNull(list)
        assertEquals(list?.size, 4)
        assertEquals(list?.get(3), itemLocal)
    }

    @Test
    fun deleteSimplePreachTest() = runBlocking {
        val itemLocal = simplePreachList[1]

        simplePreachDao.deleteSimplePreach(itemLocal)

        val list = simplePreachDao.getAllData()?.first()
        val itemDb = simplePreachDao.getSimplePreachItem("202102").first()

        assertNull(itemDb)
        assertEquals(list?.size, 6)
    }

    @Test
    fun deleteAllInsertListTest() = runBlocking {
        simplePreachDao.deleteAll()

        var list = simplePreachDao.getAllData()?.first()

        assert(list!!.isEmpty())
        simplePreachDao.insertSimplePreaches(getListSimplePreachDbEntity())
        list = simplePreachDao.getAllData()?.first()

        assertNotNull(list)
        assertEquals(list?.size, 7)

    }
}

fun getListSimplePreachDbEntity(): List<SimplePreachDbEntity> {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return listOf(
        SimplePreachDbEntity(
            id = 1,
            preached = true,
            study = 0,
            date = formatter.parse("2021-01-06")!!
        ),
        SimplePreachDbEntity(
            id = 2,
            preached = true,
            study = 0,
            date = formatter.parse("2021-02-06")!!
        ),
        SimplePreachDbEntity(
            id = 3,
            preached = true,
            study = 0,
            date = formatter.parse("2021-03-06")!!
        ),
        SimplePreachDbEntity(
            id = 4,
            preached = true,
            study = 0,
            date = formatter.parse("2021-04-06")!!
        ),
        SimplePreachDbEntity(
            id = 5,
            preached = true,
            study = 0,
            date = formatter.parse("2021-05-06")!!
        ),
        SimplePreachDbEntity(
            id = 6,
            preached = true,
            study = 0,
            date = formatter.parse("2021-06-06")!!
        ),
        SimplePreachDbEntity(
            id = 7,
            preached = true,
            study = 0,
            date = formatter.parse("2021-07-06")!!
        ),
    )
}