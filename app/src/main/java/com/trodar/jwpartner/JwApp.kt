package com.trodar.jwpartner

import android.app.Application
import com.trodar.common.Core
import com.trodar.common.CoreProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class JwApp: Application() {

    @Inject lateinit var coreProvider: CoreProvider

    override fun onCreate() {
        super.onCreate()
        Core.init(coreProvider)
    }
}