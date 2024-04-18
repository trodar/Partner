package com.trodar.room.simplepreach

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trodar.common.Core
import com.trodar.room.model.SimplePreach
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = Core.databaseConst.SIMPLE_PREACH_TABLE_NAME)
data class SimplePreachDbEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val preached: Boolean,
    override val study: Int,
    override val date: Date
): SimplePreach, Parcelable