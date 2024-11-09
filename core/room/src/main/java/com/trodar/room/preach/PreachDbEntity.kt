package com.trodar.room.preach

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trodar.common.Core
import com.trodar.room.model.Preach
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "preach_table")
data class PreachDbEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val time: Int,
    override val publication: Int,
    override val video: Int,
    override val returns: Int,
    override val study: Int,
    override val description: String,
    override val date: Date
) : Preach, Parcelable