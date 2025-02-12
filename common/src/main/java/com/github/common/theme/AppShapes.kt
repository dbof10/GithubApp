package com.github.common.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.staticCompositionLocalOf

private val Shapes = Shapes(
    // Custom shapes here
    medium = RoundedCornerShape(AppDimensions.cornerMedium)
)


internal val LocalAppShapes = staticCompositionLocalOf { Shapes }
