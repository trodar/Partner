package com.trodar.report.domain

import com.trodar.room.PreachRepository
import com.trodar.room.model.Preach
import javax.inject.Inject

class PreachDeleteUseCase @Inject constructor(
    private val preachRepository: PreachRepository
) {
    suspend fun deletePreach(preach: Preach) {
        preachRepository.deletePreach(preach)
    }

}