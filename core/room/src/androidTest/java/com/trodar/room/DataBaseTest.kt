package com.trodar.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class DataBaseTest {

    lateinit var db: JwPartnerDatabase

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), JwPartnerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb()  {
        db.close()
    }

}