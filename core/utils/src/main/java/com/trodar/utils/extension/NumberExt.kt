package com.trodar.utils.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

//fun Double.format(scale: Int) = "%.${scale}f".format(this)
fun Int.format(scale: Int = 2) = "%0${scale}d".format(this)
@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }