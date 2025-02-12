package com.github.common.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val PrimaryShade500Color = Color(0xFFD0BCFF)
internal val SecondaryShade500Color = Color(0xFFEFB8C8)
internal val SurfaceWhiteColor = Color(0xFFFFFFFF)
internal val SurfaceBlackColor = Color(0xFF000000)

data class AppColors(
    val themeColors: ColorScheme,
    val primaryColor: PrimaryColor = PrimaryColor(
        shade500 = PrimaryShade500Color,
    ),
    val secondaryColor: SecondaryColor = SecondaryColor(
        shade500 = SecondaryShade500Color,
    ),
    val shadeColor: ShadeColor = ShadeColor(
        white = SurfaceWhiteColor,
        black = SurfaceBlackColor,
    ),
)

data class PrimaryColor(
    val shade500: Color,
)

data class SecondaryColor(
    val shade500: Color,
)

data class ShadeColor(
    val white: Color,
    val black: Color,
)

internal val LightColorPalette = AppColors(
    themeColors = lightColorScheme(
        primary = PrimaryShade500Color,
        secondary = SecondaryShade500Color,
        surface = SurfaceWhiteColor,
        background = SurfaceWhiteColor,
    )
)

internal val DarkColorPalette = AppColors(
    // Config for dark mode
    themeColors = darkColorScheme()
)

internal val LocalAppColors = staticCompositionLocalOf { LightColorPalette }
