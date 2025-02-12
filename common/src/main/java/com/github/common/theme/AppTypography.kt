package com.github.common.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

interface AppTypography {
    val t1: TextStyle
        get() = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = AppDimensions.fontSizeXLarge,
        )
    val t2: TextStyle
        get() = t1.copy(
            fontSize = AppDimensions.fontSizeLarge,
        )
    val t3: TextStyle
        get() = t1.copy(
            fontSize = AppDimensions.fontSizeMedium,
        )

    val b1: TextStyle
        get() = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = AppDimensions.fontSizeLarge,
        )
    val b2: TextStyle
        get() = b1.copy(
            fontWeight = FontWeight.Normal,
            fontSize = AppDimensions.fontSizeMedium,
        )
    val b3: TextStyle
        get() = b1.copy(
            fontWeight = FontWeight.Normal,
            fontSize = AppDimensions.fontSizeSmall,
        )

    val themeTypography: Typography
        get() = typographyFromDefaults(
            titleLarge = t1,
            titleMedium = t2,
            titleSmall = t3,
            bodyLarge = b1,
            bodyMedium = b2,
            bodySmall = b3
        )
}

object AppTypographyImpl : AppTypography

private fun typographyFromDefaults(
    titleLarge: TextStyle? = null,
    titleMedium: TextStyle? = null,
    titleSmall: TextStyle? = null,
    bodyLarge: TextStyle? = null,
    bodyMedium: TextStyle? = null,
    bodySmall: TextStyle? = null,
): Typography {
    val defaults = Typography()
    return Typography(
        titleLarge = defaults.titleLarge.merge(titleLarge),
        titleMedium = defaults.titleMedium.merge(titleMedium),
        titleSmall = defaults.titleSmall.merge(titleSmall),
        bodyLarge = defaults.bodyLarge.merge(bodyLarge),
        bodyMedium = defaults.bodyMedium.merge(bodyMedium),
        bodySmall = defaults.bodySmall.merge(bodySmall),
    )
}

internal val LocalAppTypography = staticCompositionLocalOf<AppTypography> { AppTypographyImpl }
