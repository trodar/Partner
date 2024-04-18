package com.trodar.report.domain.backup

import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trodar.common.constants.DataBaseConstant.NOTEPAD_TABLE_NAME
import com.trodar.common.constants.DataBaseConstant.PREACH_TABLE_NAME
import com.trodar.common.constants.DataBaseConstant.SIMPLE_PREACH_TABLE_NAME
import com.trodar.common.constants.ExportConstant.FILE_EXT
import com.trodar.room.NotepadRepository
import com.trodar.room.PreachRepository
import com.trodar.room.SimplePreachRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.io.BufferedOutputStream
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class ExportDataBase @AssistedInject constructor(
    private val preachRepository: PreachRepository,
    private val simplePreachRepository: SimplePreachRepository,
    private val notepadRepository: NotepadRepository,
    @Assisted outputStream: OutputStream,
) {
    private val zip: ZipOutputStream = ZipOutputStream(BufferedOutputStream(outputStream))

    @Throws(IOException::class)
    fun run()  {
        val exportDir = File(
            Environment.getExternalStorageDirectory(),
            "/partner_backup"
        )
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }
        runBlocking {

            zip.use { _ ->
                val gson: Gson = GsonBuilder().create()

                val preach = preachRepository.getAllData().firstOrNull()
                if (!preach.isNullOrEmpty()) {
                    val preachJson = gson.toJson(preach)
                    exportTable(preachJson, PREACH_TABLE_NAME)
                }

                val simplePreach = simplePreachRepository.getAllData()?.firstOrNull()
                if (!simplePreach.isNullOrEmpty()) {
                    val simplePreachJson = gson.toJson(simplePreach)
                    exportTable(simplePreachJson, SIMPLE_PREACH_TABLE_NAME)
                }

                val notepad = notepadRepository.getList().firstOrNull()
                if (!notepad.isNullOrEmpty()) {
                    val notepadJson = gson.toJson(notepad)
                    Log.d("mack", notepadJson)
                    exportTable(notepadJson, NOTEPAD_TABLE_NAME)
                }
            }
        }
    }

    private fun exportTable(json: String, tableName: String) {

        val entry = ZipEntry("$tableName$FILE_EXT")
        zip.putNextEntry(entry)
        zip.write(json.toByteArray())
        zip.closeEntry()
    }

    @AssistedFactory
    interface ExportDataBaseFactory {
        fun create(outputStream: OutputStream): ExportDataBase
    }
}