package com.trodar.report.domain

import com.trodar.domain.preach.entity.PreachEntity
import com.trodar.room.PreachRepository
import com.trodar.room.model.Preach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPreachUseCase @Inject constructor(
    private val preachRepository: PreachRepository
) {

    suspend operator fun invoke(preachId: Int): Flow<Preach> {

        return preachRepository.getPreach(preachId = preachId).map { preach ->
            return@map PreachEntity(
                preach.id,
                preach.time,
                preach.publication,
                preach.video,
                preach.returns,
                preach.study,
                preach.description,
                preach.date
            )
        }
    }
}