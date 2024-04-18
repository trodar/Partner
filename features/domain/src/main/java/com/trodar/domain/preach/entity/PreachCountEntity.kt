package com.trodar.domain.preach.entity

import androidx.annotation.StringRes
import com.trodar.common.entity.CountEntity
import com.trodar.room.model.Preach
import com.trodar.theme.R
import kotlin.math.roundToInt

data class PreachCountEntity(
    val publication: PreachCountItem = PreachCountItem(
        CountEntity.PreachType.PUBLICATION,
        0,
        R.string.preach_publication
    ),
    val returns: PreachCountItem = PreachCountItem(
        CountEntity.PreachType.RETURN,
        0,
        R.string.preach_return
    ),
    val study: PreachCountItem = PreachCountItem(
        CountEntity.PreachType.STUDY,
        0,
        R.string.preach_study
    ),
    val video: PreachCountItem = PreachCountItem(
        CountEntity.PreachType.VIDEO,
        0,
        R.string.preach_video
    ),
    val startTime: PreachCountItem = PreachCountItem(
        CountEntity.PreachType.START_TIME,
        0,
        R.string.preach_start
    ),
    val minutes: PreachCountItem = PreachCountItem(
        CountEntity.PreachType.MINUTES,
        0,
        R.string.preach_start
    ),
    val alarm: PreachCountItem = PreachCountItem(
        CountEntity.PreachType.ALARM,
        0,
        R.string.preach_start
    ),
    val allMinutes: PreachCountItem = PreachCountItem(
        CountEntity.PreachType.ALL_MINUTES,
        0,
        R.string.preach_start
    )
) {

    companion object Mapper {
        fun mapFromCountEntity(preachCountDataEntity: CountEntity): PreachCountEntity {


            val allMinutes =
                if (preachCountDataEntity.startTime == 0L) preachCountDataEntity.minutes
                else (preachCountDataEntity.minutes + (System.currentTimeMillis() - preachCountDataEntity.startTime) / 60000)
                    .toFloat().roundToInt()

            return PreachCountEntity(
                publication = PreachCountItem(
                    CountEntity.PreachType.PUBLICATION,
                    preachCountDataEntity.publication.toLong(),
                    R.string.preach_publication
                ),
                returns = PreachCountItem(
                    CountEntity.PreachType.RETURN,
                    preachCountDataEntity.returns.toLong(),
                    R.string.preach_return
                ),
                study = PreachCountItem(
                    CountEntity.PreachType.STUDY,
                    preachCountDataEntity.study.toLong(),
                    R.string.preach_study
                ),
                video = PreachCountItem(
                    CountEntity.PreachType.VIDEO,
                    preachCountDataEntity.video.toLong(),
                    R.string.preach_video
                ),
                startTime = PreachCountItem(
                    CountEntity.PreachType.START_TIME,
                    preachCountDataEntity.startTime,
                    R.string.preach_return
                ),
                minutes = PreachCountItem(
                    CountEntity.PreachType.MINUTES,
                    preachCountDataEntity.minutes.toLong(),
                    R.string.preach_return
                ),
                alarm = PreachCountItem(
                    CountEntity.PreachType.ALARM,
                    preachCountDataEntity.alarm.toLong(),
                    R.string.preach_return
                ),
                allMinutes = PreachCountItem(
                    CountEntity.PreachType.ALL_MINUTES,
                    allMinutes.toLong(),
                    R.string.preach_return
                )
            )
        }

        fun mapFromPreachCountEntity(preach: Preach): PreachCountEntity {

            return PreachCountEntity(
                publication = PreachCountItem(
                    CountEntity.PreachType.PUBLICATION,
                    preach.publication.toLong(),
                    R.string.preach_publication
                ),
                returns = PreachCountItem(
                    CountEntity.PreachType.RETURN,
                    preach.returns.toLong(),
                    R.string.preach_return
                ),
                study = PreachCountItem(
                    CountEntity.PreachType.STUDY,
                    preach.study.toLong(),
                    R.string.preach_study
                ),
                video = PreachCountItem(
                    CountEntity.PreachType.VIDEO,
                    preach.video.toLong(),
                    R.string.preach_video
                ),
                startTime = PreachCountItem(
                    CountEntity.PreachType.START_TIME,
                    0,
                    R.string.preach_return
                ),
                minutes = PreachCountItem(
                    CountEntity.PreachType.MINUTES,
                    preach.time.toLong(),
                    R.string.preach_return
                ),
                alarm = PreachCountItem(
                    CountEntity.PreachType.ALARM,
                    0,
                    R.string.preach_return
                ),
                allMinutes = PreachCountItem(
                    CountEntity.PreachType.ALL_MINUTES,
                    preach.time.toLong(),
                    R.string.preach_return
                )
            )
        }
    }
}

data class PreachCountItem(
    val preachType: CountEntity.PreachType,
    val count: Long,
    @StringRes val textId: Int
)

























