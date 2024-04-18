package com.trodar.report.domain


import com.trodar.domain.preach.entity.PreachMonthPeriodEntity
import com.trodar.domain.preach.entity.PreachYearsPeriodEntity
import com.trodar.domain.preach.entity.SimplePreachEntity
import com.trodar.room.PreachRepository
import com.trodar.room.SimplePreachRepository
import com.trodar.utils.extension.compareTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject


class ListPreachUseCase @Inject constructor(
    private val preachRepository: PreachRepository,
    private val simplePreachRepository: SimplePreachRepository,
) {
    private val maxMonth = "08"
    private val minMonth = "09"
    private val month = 9

    suspend fun getYearPeriodList(): Flow<PreachYearsPeriodEntity> = flow {
        val minDate = getMinDate() ?: return@flow
        var maxDate = getMaxDate()

            while (maxDate.year > minDate.year) {
            val maxPDate = maxDate.year.toString() + maxMonth
            maxDate = maxDate.minusYears(1)
            val minPDate = maxDate.year.toString() + minMonth

            val item = preachRepository.getYearPeriodList(minPDate, maxPDate).map {
                PreachYearsPeriodEntity(
                    hours = it.hours,
                    publication = it.publication,
                    video = it.video,
                    study = it.study,
                    returns = it.returns,
                    maxYear = maxDate.plusYears(1).year,
                    minYear = maxDate.year,
                )
            }.first()
            emit(item)
        }

    }

    suspend fun getSumHoursYearPeriod(minYear: String, maxYear: String): Int {
        return preachRepository.getSumHoursYearPeriod(minYear + minMonth, maxYear + maxMonth)
    }

    suspend fun getMonthsPeriodList(minYear: Int, maxYear: Int): Flow<List<PreachMonthPeriodEntity>> {
        val simpleList = simplePreachRepository.getMonthsPeriodList("$minYear$minMonth", "$maxYear$maxMonth")

        return preachRepository.getMonthsPeriodList("$minYear$minMonth", "$maxYear$maxMonth").map { list ->
            list.map { item ->
                PreachMonthPeriodEntity (
                    item,
                    simpleList?.map {
                    it.filter { simpleItem -> simpleItem.date.compareTo(item.year + item.month) }
                        .map {  item ->
                            SimplePreachEntity(
                                item.id,
                                item.preached,
                                item.study,
                                item.date,
                            )
                        }
                        .firstOrNull()
                }?.firstOrNull() )
            }
        }
    }

    private suspend fun getMinDate(): LocalDate? {
        val minDate = preachRepository.getFirstDate() ?: return null

        var localDate = minDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        if (localDate.monthValue < month)
            localDate = localDate.minusYears(1)

        return localDate!!
    }

    private fun getMaxDate(): LocalDate {
        var date = LocalDate.now()
        if (date.monthValue >= month) date = date.plusYears(1)

        return date
    }

}
