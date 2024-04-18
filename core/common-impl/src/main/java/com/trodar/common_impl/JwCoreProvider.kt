package com.trodar.common_impl

import com.trodar.common.CoreProvider
import com.trodar.common.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwCoreProvider @Inject constructor(
    override val mainActivityClass: Class<*>,
    override val preachServiceClass: Class<*>,
    override val preachRepository: Repository,
): CoreProvider