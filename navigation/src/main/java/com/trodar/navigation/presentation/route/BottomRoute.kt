package com.trodar.navigation.presentation.route

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.trodar.navigation.R

fun getBottomItems() = listOf(
    BottomRoute.Preach,
    BottomRoute.Territory,
    BottomRoute.Notepad,
    BottomRoute.Report,
)

sealed class BottomRoute(val title: BottomTitle, @DrawableRes val iconId: Int, val route: String) {

    object Preach : BottomRoute(
        BottomTitle.PREACH,
        R.drawable.ic_preach,
        BottomTitle.PREACH.toString(),
    )

    object Territory : BottomRoute(
        BottomTitle.TERRITORY,
        R.drawable.ic_territory,
        BottomTitle.TERRITORY.toString(),
    )

    object Notepad : BottomRoute(
        BottomTitle.NOTEPAD,
        R.drawable.ic_notepad,
        BottomTitle.NOTEPAD.toString(),
    )

    object Report : BottomRoute(

        BottomTitle.REPORT,
        R.drawable.ic_report,
        BottomTitle.REPORT.toString(),
    )
}

enum class BottomTitle(@StringRes val id: Int) {
    PREACH(com.trodar.theme.R.string.bottom_preach),
    REPORT(com.trodar.theme.R.string.bottom_report),
    TERRITORY(com.trodar.theme.R.string.bottom_area),
    NOTEPAD(com.trodar.theme.R.string.bottom_notepad),
}