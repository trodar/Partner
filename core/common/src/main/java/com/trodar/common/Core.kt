package com.trodar.common

import com.trodar.common.constants.DataBaseConstant
import com.trodar.common.constants.PreachConstant
import com.trodar.common.data.NeedUpdate

object Core {
    private lateinit var coreProvider: CoreProvider

    val preachConst: PreachConstant get() = PreachConstant
    val databaseConst: DataBaseConstant get() = DataBaseConstant
    val mainActivityClass get() = coreProvider.mainActivityClass
    val preachServiceClass get() = coreProvider.preachServiceClass

    val preachRepository get() = coreProvider.preachRepository

    val updateNode = NeedUpdate()


    fun init(coreProvider: CoreProvider){
        this.coreProvider = coreProvider
    }
}