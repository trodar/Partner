package com.trodar.navigation.presentation.route


sealed class ReportRoute(val route: String){
    data object YearMonth: ReportRoute("year_month")
    data object Days: ReportRoute("$DAYS/{$DAYS_YEAR_KEY}/{$DAYS_MONTH_KEY}") {
        fun passYearAndMonth(year: String, month: String): String {
            return "$DAYS/$year/$month"
        }
    }
    data object EditDay: ReportRoute("${EDIT_DAY}/{$EDIT_DAY_ID}") {
        fun passPreachId(id: Int): String {
            return "$EDIT_DAY/$id"

        }
    }

    companion object {
        const val DAYS = "days"
        const val DAYS_YEAR_KEY = "year"
        const val DAYS_MONTH_KEY = "month"
        const val EDIT_DAY = "edit_day"
        const val EDIT_DAY_ID = "id"
    }
}