package com.trodar.domain.preach.usecase

import com.trodar.domain.preach.entity.SimplePreachEntity
import com.trodar.room.SimplePreachRepository
import com.trodar.room.model.SimplePreach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SimplePreachUseCase @Inject constructor(
    private val simplePreachRepository: SimplePreachRepository
) {

    suspend fun getSimplePreach(date: String): Flow<SimplePreachEntity> {
        return simplePreachRepository.getSimplePreachItem(date).map {item ->
            if (item != null)
                SimplePreachEntity(
                    item.id,
                    item.preached,
                    item.study,
                    item.date
                )
            else SimplePreachEntity()
        }
    }

    suspend fun editSimplePreachItem(simplePreach: SimplePreach) {
        simplePreachRepository.editSimplePreachItem(simplePreach)
    }
}