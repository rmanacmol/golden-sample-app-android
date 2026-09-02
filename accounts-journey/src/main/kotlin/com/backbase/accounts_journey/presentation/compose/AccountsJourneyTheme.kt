package com.backbase.accounts_journey.presentation.compose

import android.util.TypedValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.backbase.android.design.R as DesignR

@Composable
internal fun AccountsJourneyTheme(content: @Composable () -> Unit) {
    val background = themeColor(DesignR.attr.colorBackgroundPage, Color.White)
    val onSurface = themeColor(DesignR.attr.colorForegroundDefault, Color.Black)
    val onSurfaceSecondary = themeColor(DesignR.attr.colorForegroundSupport, Color.Gray)
    val surface = themeColor(DesignR.attr.colorBackgroundSurface1, Color.White)
    MaterialTheme(
        colorScheme = lightColorScheme(
            background = background,
            surface = surface,
            onBackground = onSurface,
            onSurface = onSurface,
            onSurfaceVariant = onSurfaceSecondary,
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .semantics { testTagsAsResourceId = true },
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}

@Composable
internal fun themeColor(attr: Int, fallback: Color): Color {
    val context = LocalContext.current
    val typedValue = remember(attr) {
        TypedValue().also { value ->
            context.theme.resolveAttribute(attr, value, true)
        }
    }
    return when {
        typedValue.resourceId != 0 -> colorResource(typedValue.resourceId)
        typedValue.data != 0 -> Color(typedValue.data)
        else -> fallback
    }
}

@Composable
internal fun themeDimen(attr: Int, fallback: Dp): Dp {
    val context = LocalContext.current
    val density = LocalDensity.current
    val typedValue = remember(attr) {
        TypedValue().also { value ->
            context.theme.resolveAttribute(attr, value, true)
        }
    }
    if (typedValue.type == TypedValue.TYPE_NULL || typedValue.data == 0 && typedValue.resourceId == 0) {
        return fallback
    }
    val pixels = TypedValue.complexToDimensionPixelSize(
        typedValue.data,
        context.resources.displayMetrics
    )
    return with(density) { pixels.toDp() }
}

@Composable
internal fun spacerSmall(): Dp = themeDimen(DesignR.attr.spacerSmall, 8.dp)

@Composable
internal fun spacerMedium(): Dp = themeDimen(DesignR.attr.spacerMedium, 16.dp)

@Composable
internal fun spacerLarge(): Dp = themeDimen(DesignR.attr.spacerLarge, 24.dp)

@Composable
internal fun radiusLarge(): Dp = themeDimen(DesignR.attr.radiusLarge, 16.dp)
