package com.trodar.navigation.presentation.domain

import androidx.compose.foundation.layout.PaddingValues

data class BottomParameters (
    val args: Array<String> = arrayOf(),
    val paddingValues: PaddingValues = PaddingValues()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BottomParameters

        return args.contentEquals(other.args)
    }

    override fun hashCode(): Int {
        return args.contentHashCode()
    }
}
