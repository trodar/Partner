package com.trodar.common_impl

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.trodar.common.Repository
import com.trodar.common.entity.CountEntity
import com.trodar.common_impl.entity.PreachCountDataEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreachSharedRepository @Inject constructor(
    @ApplicationContext val context: Context,
    preferences: SharedPreferences,
) : Repository, SharedPreferences.OnSharedPreferenceChangeListener {

    private val pref = preferences


    private val preachCountFlow = MutableStateFlow(PreachCountDataEntity())

    init {
        pref.registerOnSharedPreferenceChangeListener(this)
    }

    override fun String.put(long: Long): PreachSharedRepository {
        pref.edit {
            putLong(this@put, long)
        }
        return this@PreachSharedRepository
    }

    override fun String.put(int: Int): PreachSharedRepository {
        pref.edit {
            putInt(this@put, int)
        }
        return this@PreachSharedRepository
    }

    override fun String.remove(): PreachSharedRepository {
        val name = this
        pref.edit {
            remove(name)
        }
        return this@PreachSharedRepository
    }


    override fun String.getInt() = pref.getInt(this, 0)
    override fun String.getLong() = pref.getLong(this, 0)


    override fun setMinutes(minutes: Int): Repository {
        return CountEntity.PreachType.MINUTES.name.put(minutes)
    }

    override fun setStartTime(time: Long): Repository {
        return CountEntity.PreachType.START_TIME.name.put(time)
    }

    override fun setAlarm(minutes: Int): Repository {
        return CountEntity.PreachType.ALARM.name.put(minutes)
    }

    override fun setPublication(value: Int): Repository {
        return CountEntity.PreachType.PUBLICATION.name.put(value)
    }

    override fun setReturn(value: Int): Repository {
        return CountEntity.PreachType.RETURN.name.put(value)
    }

    override fun setStudy(value: Int): Repository {
        return CountEntity.PreachType.STUDY.name.put(value)
    }

    override fun setVideo(value: Int): Repository {
        return CountEntity.PreachType.VIDEO.name.put(value)
    }

    override fun removeStartTime(): Repository {
        return CountEntity.PreachType.START_TIME.name.remove()
    }

    override fun removeMinutes(): Repository {
        return CountEntity.PreachType.MINUTES.name.remove()
    }

    override fun removeAlarm(): Repository {
        return CountEntity.PreachType.ALARM.name.remove()
    }

    override fun removePublication(): Repository {
        return CountEntity.PreachType.PUBLICATION.name.remove()
    }

    override fun removeReturn(): Repository {
        return CountEntity.PreachType.RETURN.name.remove()
    }

    override fun removeStudy(): Repository {
        return CountEntity.PreachType.STUDY.name.remove()
    }

    override fun removeVideo(): Repository {
        return CountEntity.PreachType.VIDEO.name.remove()
    }

    override fun getStartTime(): Long = CountEntity.PreachType.START_TIME.name.getLong()

    override fun getMinutes(): Int = CountEntity.PreachType.MINUTES.name.getInt()

    override fun getAlarm(): Int = CountEntity.PreachType.ALARM.name.getInt()

    override fun getPublication(): Int = CountEntity.PreachType.PUBLICATION.name.getInt()

    override fun getReturn(): Int = CountEntity.PreachType.RETURN.name.getInt()

    override fun getStudy(): Int = CountEntity.PreachType.STUDY.name.getInt()

    override fun getVideo(): Int = CountEntity.PreachType.VIDEO.name.getInt()

    override suspend fun preachData(): StateFlow<CountEntity> {
        return preachCountFlow.asStateFlow()
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            CountEntity.PreachType.RETURN.name -> preachCountFlow.value =
                preachCountFlow.value.copy(
                    returns = getReturn()
                )

            CountEntity.PreachType.VIDEO.name -> preachCountFlow.value =
                preachCountFlow.value.copy(
                    video = getVideo()
                )

            CountEntity.PreachType.STUDY.name -> preachCountFlow.value =
                preachCountFlow.value.copy(
                    study = getStudy()
                )

            CountEntity.PreachType.PUBLICATION.name -> preachCountFlow.value =
                preachCountFlow.value.copy(
                    publication = getPublication()
                )

            CountEntity.PreachType.START_TIME.name -> preachCountFlow.value =
                preachCountFlow.value.copy(
                    startTime = getStartTime()
                )

            CountEntity.PreachType.MINUTES.name -> preachCountFlow.value =
                preachCountFlow.value.copy(
                    minutes = getMinutes()
                )

            CountEntity.PreachType.ALARM.name -> preachCountFlow.value =
                preachCountFlow.value.copy(
                    alarm = getAlarm()
                )
        }
    }
}































