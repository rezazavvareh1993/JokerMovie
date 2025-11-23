package com.rezazavareh7.designsystem.component.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.NavigationBarItemComponent(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors =
            NavigationBarItemDefaults.colors(
                selectedIconColor = PalizNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = PalizNavigationDefaults.navigationContentColor(),
                selectedTextColor = PalizNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = PalizNavigationDefaults.navigationContentColor(),
                indicatorColor = PalizNavigationDefaults.navigationIndicatorColor(),
            ),
    )
}

@Composable
fun NavigationBarComponent(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
    ) {
        NavigationBar(
            modifier = modifier,
            contentColor = PalizNavigationDefaults.navigationContentColor(),
            tonalElevation = 0.dp,
            content = content,
        )
    }
}

object PalizNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun NavigationBarPreview() {
    NavigationBarComponent {
        NavigationBarItemComponent(
            selected = true,
            onClick = {},
            icon = { },
            label = { Text("Home1") },
        )
        NavigationBarItemComponent(
            selected = false,
            onClick = {},
            icon = { },
            label = { Text("Home2") },
        )
        NavigationBarItemComponent(
            selected = false,
            onClick = {},
            icon = { },
            label = { Text("Home3") },
        )
    }
}
