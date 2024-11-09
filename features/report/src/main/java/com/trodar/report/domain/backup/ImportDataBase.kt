package com.trodar.report.domain.backup

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.trodar.common.constants.DataBaseConstant
import com.trodar.common.constants.ExportConstant
import com.trodar.room.NotepadRepository
import com.trodar.room.PreachRepository
import com.trodar.room.SimplePreachRepository
import com.trodar.room.notepad.NotepadDbEntity
import com.trodar.room.preach.PreachDbEntity
import com.trodar.room.simplepreach.SimplePreachDbEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.zip.ZipInputStream

data class UnzippedFile(val filename: String, val content: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UnzippedFile

        if (filename != other.filename) return false
        return content.contentEquals(other.content)
    }

    override fun hashCode(): Int {
        var result = filename.hashCode()
        result = 31 * result + content.contentHashCode()
        return result
    }
}

class ImportDataBase @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    private val preachRepository: PreachRepository,
    private val simplePreachRepository: SimplePreachRepository,
    private val notepadRepository: NotepadRepository,
    @Assisted private val inputStream: InputStream
) {

    private val session = "session.xml"
    private val dateFormat = "MMM dd, yyyy HH:mm:ss"

    @Throws(IOException::class, Exception::class)
    fun run(): Boolean {

        return runBlocking {
            val unzipped = unzip()

            unzipped.forEach { file ->

                val data = file.content.toString(Charset.defaultCharset())


                when (file.filename) {
                    "preach_table${ExportConstant.FILE_EXT}" -> {
                        loadPreachData(data)
                    }

                    "notepad_table${ExportConstant.FILE_EXT}" -> {
                        loadNotepadData(data)
                    }
                    "simple_preach_table${ExportConstant.FILE_EXT}" -> {
                        loadSimplePreachData(data)
                    }
                    session -> {
                        loadPReachFromJwDroid(data)
                    }
                }
            }

            return@runBlocking true
        }
    }

    private suspend fun loadPReachFromJwDroid(data: String) {
        val entryInput = ByteArrayInputStream(data.toByteArray())
        val list = XMLParser.parse("session", entryInput)

        if (list.isEmpty()) throw Exception(context.getString(com.trodar.theme.R.string.jwdroid_parse_error, session))

        val preachList: List<PreachDbEntity> = list.map { item ->
            val date = item["date"]
            val localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
                PreachDbEntity(
                    id = item["ROWID"]?.toInt() ?: 0,
                    description = item["desc"] ?: "",
                    date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                    study =  0,
                    returns = item["returns"]?.toInt() ?: 0,
                    video = item["videos"]?.toInt() ?: 0,
                    publication = item["publications"]?.toInt() ?: 0,
                    time = item["minutes"]?.toInt() ?: 1
                )
        }

        if (preachList.isNotEmpty()) {
            preachRepository.deleteAll()
            preachRepository.insertPreaches(preachList)
        }

    }

    private suspend fun loadNotepadData(data: String) {
        val gson: Gson = GsonBuilder()
            .setDateFormat(dateFormat)
            .setLenient()
            .create()
         val itemType = object : TypeToken<List<NotepadDbEntity>>() {}.type
        try {
            val notepad = gson.fromJson<List<NotepadDbEntity>>(data.trim(), itemType)
            notepadRepository.deleteAll()
            notepadRepository.insertNotepads(notepad)
         }
        catch (e: Exception) {
            Log.e("mack", e.toString())
        }

    }

    private suspend fun loadSimplePreachData(data: String) {
        val gson: Gson = GsonBuilder()
            .setDateFormat(dateFormat)
            .setLenient()
            .create()
        val itemType = object : TypeToken<List<SimplePreachDbEntity>>() {}.type
        try {
            val preach = gson.fromJson<List<SimplePreachDbEntity>>(data.trim(), itemType)
            simplePreachRepository.deleteAll()
            simplePreachRepository.insertSimplePreaches(preach)
        }
        catch (e: Exception) {
            Log.e("mack", e.toString())
        }

    }

    private suspend fun loadPreachData(data: String) {
        val gson: Gson = GsonBuilder()
            .setDateFormat(dateFormat)
            .setLenient()
            .create()
        val itemType = object : TypeToken<List<PreachDbEntity>>() {}.type
        try {
            val preach = gson.fromJson<List<PreachDbEntity>>(data.trim(), itemType)
            preachRepository.deleteAll()
            preachRepository.insertPreaches(preach)
        }
        catch (e: Exception) {
            Log.e("mack", e.toString())
        }

    }

    private fun unzip(): List<UnzippedFile> = ZipInputStream(BufferedInputStream(inputStream))
        .use { zipInputStream ->
            generateSequence { zipInputStream.nextEntry }
                .filterNot { it.isDirectory }
                .map {
                    UnzippedFile(
                        filename = it.name,
                        content = zipInputStream.readBytes()
                    )
                }.toList()
        }

    @AssistedFactory
    interface ImportDataBaseFactory {
        fun create(inputStream: InputStream): ImportDataBase
    }
}