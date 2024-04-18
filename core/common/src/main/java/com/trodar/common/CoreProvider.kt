package com.trodar.common

interface CoreProvider {

    val mainActivityClass: Class<*>
    val preachServiceClass: Class<*>
    val preachRepository: Repository

}