package com.github.common.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppDimensions {
    // Corner radius values
    val cornerSmall = 8.dp
    val cornerMedium = 16.dp

    // Font size
    val fontSizeSmall = 10.sp
    val fontSizeMedium = 12.sp
    val fontSizeLarge = 16.sp
    val fontSizeXLarge = 20.sp

    // Spacing values
    val spacingXXSmall = 4.dp
    val spacingXSmall = 8.dp
    val spacingSmall = 12.dp
    val spacingMedium = 16.dp

    // Shadow values
    val shadowMedium = 8.dp
}

internal val LocalAppDimensions = staticCompositionLocalOf { AppDimensions }
