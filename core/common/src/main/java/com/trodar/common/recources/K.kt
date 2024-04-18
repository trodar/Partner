package com.trodar.common.recources

import com.trodar.common.entity.PreachItemTestTag

object K {

    private const val navCount = 4
    private const val preachItemCount = 5
    object NavigationTag {
        const val animated_navigation_bar = "animated_navigation_bar"

        val navigation_bar_item_column = MutableList(navCount){
            "navigation_bar_item_column_${it}"
        }
    }

    object PreachTag {
        const val timeRow = "preach_time_row"
        const val timeButtonMinus = "preach_time_button_minus"
        const val timeButtonPlus = "preach_time_button_plus"
        const val timeTextTime = "preach_time_text_time"

        const val serviceRow = "preach_service_row"
        const val serviceButtonStart = "preach_service_button_Start"
        const val serviceButtonPause = "preach_service_button_Pause"
        const val serviceButtonFinish = "preach_service_button_Finish"
        const val serviceButtonCancel = "preach_service_button_Cancel"
        const val serviceButtonSave = "preach_service_button_Save"
        const val serviceEditTestDescription = "preach_service_editText_description"

        val preachItemList = MutableList(preachItemCount) {
            PreachItemTestTag(
                "preach_item_row_container_${it}",
                "preach_item_minus_button_${it}",
                "preach_item_text_number_${it}",
                "preach_item_plus_button_${it}"
            )
        }

        const val simpleCheckBox = "simple_item_checkbox"
    }

    object ReportTag {
        const val yearItem = "year_item_"
        const val monthItem = "month_item_"
        const val dayItem = "day_item_"
    }

    object Screens {
        const val preachScreen = "preach_screen"
        const val reportScreen = "report_screen"
    }
}

