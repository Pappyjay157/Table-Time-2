package com.example.tabletime

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

data class Windowsize(
    val width : WindowType,
    val height : WindowType

)
enum class WindowType{
    SMALL,
    MEDIUM,
    LARGE
}
@Composable
fun rememberWindowsSize():Windowsize{
    val configuration = LocalConfiguration.current
    return Windowsize(
        width = when{
            configuration.screenWidthDp<600-> WindowType.SMALL
            configuration.screenWidthDp<840-> WindowType.MEDIUM
            else -> WindowType.LARGE
        },

        height = when {
            configuration.screenWidthDp < 480 -> WindowType.SMALL
            configuration.screenWidthDp < 900 -> WindowType.MEDIUM
            else -> WindowType.LARGE
        }
    )


}