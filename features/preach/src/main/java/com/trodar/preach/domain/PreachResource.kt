package com.trodar.preach.domain

class PreachResource<T>(val status: Status, val data: T?) {
    enum class Status {
        DEFAULT, START, PAUSE, FINISH
    }
    companion object {
        fun <T> defaults(data: T?): PreachResource<T> {
            return PreachResource(Status.DEFAULT, data)
        }

        fun <T> start(data: T?): PreachResource<T> {
            return PreachResource(Status.START, data)
        }

        fun <T> pause(data: T?): PreachResource<T> {
            return PreachResource(Status.PAUSE, data)
        }

        fun <T> finish(data: T?): PreachResource<T> {
            return PreachResource(Status.FINISH, data)
        }
    }
}