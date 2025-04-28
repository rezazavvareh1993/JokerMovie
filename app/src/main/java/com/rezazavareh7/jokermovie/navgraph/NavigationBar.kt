package com.rezazavareh7.jokermovie.navgraph

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.navigation.BottomScreens
import com.rezazavareh7.designsystem.component.navigation.NavigationBarComponent
import com.rezazavareh7.designsystem.component.navigation.NavigationBarItemComponent
import com.rezazavareh7.designsystem.component.text.label.LabelLargeTextComponent
import com.rezazavareh7.designsystem.component.text.label.LabelMediumTextComponent
import com.rezazavareh7.ui.components.ShowToast

@Composable
fun NavigationBar(
    navController: NavHostController,
    selectedItemIndex: Int,
    isNavigateFromOut: Boolean = false,
    onItemSelected: (Int) -> Unit,
) {
    var isNavigateFromOutToSpecificItem by remember {
        mutableStateOf(isNavigateFromOut)
    }
    val items =
        remember {
            listOf(
                BottomScreens.Home,
            )
        }

    var toastMessage by remember {
        mutableStateOf("")
    }

    if (toastMessage.isNotEmpty()) {
        ShowToast(context = LocalContext.current, message = toastMessage)
    }
    var lastBottomSheetScreenRoot by remember {
        mutableStateOf(items[selectedItemIndex].route.toString())
    }

    if (isNavigateFromOutToSpecificItem) {
        lastBottomSheetScreenRoot = items[selectedItemIndex].route.toString()
        navController.navigate(items[selectedItemIndex].route) {
            popUpTo(0) {
                inclusive = true
                saveState = false
            }
            launchSingleTop = true
            restoreState = false
        }
        isNavigateFromOutToSpecificItem = false
    }

    NavigationBarComponent {
        items.forEachIndexed { index, screen ->
            NavigationBarItemComponent(
                selected = isSelected(index, selectedItemIndex),
                onClick = {
                    if (lastBottomSheetScreenRoot != screen.route.toString()) {
                        lastBottomSheetScreenRoot = screen.route.toString()
                        onItemSelected(index)
                        navController.navigate(screen.route) {
                            popUpTo(0) {
                                inclusive = true
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                },
                icon = {
                    GetSelectedIcon(
                        screen = screen,
                        itemIndex = index,
                        selectedIndex = selectedItemIndex,
                    )
                },
                label = {
                    if (isSelected(index, selectedItemIndex)) {
                        LabelLargeTextComponent(text = stringResource(id = screen.name))
                    } else {
                        LabelMediumTextComponent(text = stringResource(id = screen.name))
                    }
                },
            )
        }
    }
}

private fun isSelected(
    itemIndex: Int,
    selectedIndex: Int,
) = itemIndex == selectedIndex

@Composable
fun GetSelectedIcon(
    screen: BottomScreens<out Any>,
    itemIndex: Int,
    selectedIndex: Int,
) {
    IconComponent(
        drawableId = screen.icon,
        tint =
            if (isSelected(itemIndex, selectedIndex)) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
    )
}
