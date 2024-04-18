package com.trodar.report.domain

import com.trodar.room.PreachRepository
import com.trodar.room.model.Preach
import javax.inject.Inject

class PreachUpdateUseCase @Inject constructor(
    private val preachRepository: PreachRepository
) {
    suspend operator fun invoke(preach: Preach) {
        preachRepository.editPreach(preach)
    }
}