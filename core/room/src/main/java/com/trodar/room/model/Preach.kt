package com.trodar.room.model

import android.content.Context
import java.util.Date

interface Preach : PreachBasic {
    val id: Int
    val time: Int
    val description: String
    val date: Date

    fun getPreachInfo(context: Context): String {

        var result = ""
        val resources = context.resources
        result += if (publication > 0)
            resources.getQuantityString(com.trodar.theme.R.plurals.plurals_publications, publication, publication)
        else ""

        if (video > 0) {
            if (result.isNotEmpty())
                result += ", "
            result += resources.getQuantityString(com.trodar.theme.R.plurals.plurals_video, video, video)
        }

        if (returns > 0) {
            if (result.isNotEmpty())
                result += ", "
            result += resources.getQuantityString(com.trodar.theme.R.plurals.plurals_return, returns, returns)
        }

        if (study > 0) {
            if (result.isNotEmpty())
                result += ", "
            result += resources.getQuantityString(com.trodar.theme.R.plurals.plurals_study, study, study)
        }
        return result
    }
}