package com.rezazavareh7.movies.ui.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.button.segmentbutton.singlechoice.SegmentButtonItem
import com.rezazavareh7.designsystem.component.button.segmentbutton.singlechoice.SingleChoiceSegmentButtonComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleSmallTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent

@Composable
fun SettingScreen(
    settingUiEvent: (SettingUiEvent) -> Unit,
    settingUiState: SettingUiState,
    onBackClicked: () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        ToolbarComponent(
            hasBackButton = true,
            onBackClicked = onBackClicked,
            startContent = {
                TitleMediumTextComponent(text = "تنظیمات")
            },
        )
    }) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TitleSmallTextComponent(text = "تم")
                SingleChoiceSegmentButtonComponent(
                    options =
                        linkedMapOf(
                            ThemeSegmentButtonType.LIGHT.name to SegmentButtonItem("Light", null),
                            ThemeSegmentButtonType.DARK.name to SegmentButtonItem("Dark", null),
                        ),
                    colors =
                        SegmentedButtonDefaults.colors(
                            activeContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            inactiveContainerColor =
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.7f,
                                ),
                            activeBorderColor = MaterialTheme.colorScheme.outline,
                            inactiveBorderColor = MaterialTheme.colorScheme.outline,
                            activeContentColor = MaterialTheme.colorScheme.surface,
                            inactiveContentColor = MaterialTheme.colorScheme.surface,
                        ),
                    selectedIndex = settingUiState.selectedThemeSegmentButtonIndex,
                    buttonClicked = { key, index ->
                        settingUiEvent(
                            SettingUiEvent.OnThemeSegmentButtonClicked(
                                index,
                                ThemeSegmentButtonType.valueOf(key).toString(),
                            ),
                        )
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TitleSmallTextComponent(text = "زبان")
                SingleChoiceSegmentButtonComponent(
                    options =
                        linkedMapOf(
                            LanguageSegmentButtonType.ENGLISH.name to
                                SegmentButtonItem(
                                    "English",
                                    null,
                                ),
                            LanguageSegmentButtonType.FARSI.name to
                                SegmentButtonItem(
                                    "Farsi",
                                    null,
                                ),
                        ),
                    selectedIndex = settingUiState.selectedLanguageSegmentButtonIndex,
                    buttonClicked = { key, index ->
                        settingUiEvent(
                            SettingUiEvent.OnLanguageSegmentButtonClicked(
                                index,
                                LanguageSegmentButtonType.valueOf(key).toString(),
                            ),
                        )
                    },
                )
            }
        }
    }
}
