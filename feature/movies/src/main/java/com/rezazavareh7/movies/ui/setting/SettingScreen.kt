package com.rezazavareh7.movies.ui.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.button.segmentbutton.singlechoice.SegmentButtonItem
import com.rezazavareh7.designsystem.component.button.segmentbutton.singlechoice.SingleChoiceSegmentButtonComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.R as DesignSystemResource
import com.rezazavareh7.movies.R as MovieResource

@Composable
fun SettingScreen(
    settingUiEvent: (SettingUiEvent) -> Unit,
    settingUiState: SettingUiState,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolbarComponent(
                hasBackButton = true,
                onBackClicked = onBackClicked,
                startContent = {
                    TitleMediumTextComponent(text = stringResource(DesignSystemResource.string.setting))
                },
            )
        },
        bottomBar = {
            BodyMediumTextComponent(
                modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(bottom = 32.dp),
                text = stringResource(MovieResource.string.version, settingUiState.version),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
        ) {
            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TitleMediumTextComponent(text = stringResource(MovieResource.string.theme))
                SingleChoiceSegmentButtonComponent(
                    options =
                        linkedMapOf(
                            ThemeSegmentButtonType.LIGHT.name to
                                SegmentButtonItem(
                                    stringResource(MovieResource.string.light),
                                    LocalJokerIconPalette.current.icSun,
                                ),
                            ThemeSegmentButtonType.DARK.name to
                                SegmentButtonItem(
                                    stringResource(MovieResource.string.dark),
                                    LocalJokerIconPalette.current.icMoon,
                                ),
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
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TitleMediumTextComponent(text = stringResource(MovieResource.string.language))
                SingleChoiceSegmentButtonComponent(
                    options =
                        linkedMapOf(
                            LanguageSegmentButtonType.ENGLISH.name to
                                SegmentButtonItem(
                                    stringResource(MovieResource.string.english),
                                    null,
                                ),
                            LanguageSegmentButtonType.FARSI.name to
                                SegmentButtonItem(
                                    stringResource(MovieResource.string.farsi),
                                    null,
                                ),
                        ),
                    selectedIndex = settingUiState.selectedLanguageSegmentButtonIndex,
                    buttonClicked = { key, index ->
                        settingUiEvent(
                            SettingUiEvent.OnLanguageSegmentButtonClicked(
                                index,
                                LanguageSegmentButtonType.valueOf(key).locale,
                            ),
                        )
                    },
                )
            }
        }
    }
}
