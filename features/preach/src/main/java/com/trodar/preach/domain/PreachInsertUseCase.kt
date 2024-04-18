package com.trodar.preach.domain

import com.trodar.room.PreachRepository
import com.trodar.room.model.Preach
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreachInsertUseCase @Inject constructor(
    private val preachRepository: PreachRepository
) {


    suspend fun insertPreach(preach: Preach) {
        preachRepository.editPreach(preach)
    }
}