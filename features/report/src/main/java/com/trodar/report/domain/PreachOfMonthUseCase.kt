package com.trodar.report.domain

import com.trodar.domain.preach.entity.PreachEntity
import com.trodar.room.PreachRepository
import com.trodar.room.model.Preach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreachOfMonthUseCase @Inject constructor(
    private val preachRepository: PreachRepository
) {
    suspend fun getPreachOfCurrentMonthList(date: String): Flow<List<Preach>> {
        return preachRepository.getPreachesOfMonth(date).map {list ->
            list.map { item ->
                PreachEntity(
                    item.id,
                    item.time,
                    item.publication,
                    item.video,
                    item.returns,
                    item.study,
                    item.description,
                    item.date
                )
            }
        }
    }

}