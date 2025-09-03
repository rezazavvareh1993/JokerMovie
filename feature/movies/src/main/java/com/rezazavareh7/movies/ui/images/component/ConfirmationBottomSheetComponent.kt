package com.rezazavareh7.movies.ui.images.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.button.FilledButtonComponent
import com.rezazavareh7.designsystem.component.button.OutlinedButtonComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.theme.JokerMovieTheme
import com.rezazavareh7.movies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationBottomSheetComponent(
    title: String,
    subTitle: String,
    confirmationButtonTitle: String,
    isErrorMode: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier =
            Modifier
                .navigationBarsPadding(),
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        ) {
            ToolbarComponent(
                isBottomSheet = true,
                backgroundColor = Color.Transparent,
                startContent = {
                    TitleLargeTextComponent(text = title)
                },
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
            ) {
                BodyMediumTextComponent(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    text = subTitle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FilledButtonComponent(
                        modifier =
                            Modifier.weight(0.5f),
                        containerColor = if (isErrorMode) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        onClick = {
                            onConfirm()
                        },
                        text = {
                            TitleMediumTextComponent(text = confirmationButtonTitle, color = MaterialTheme.colorScheme.surface)
                        },
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButtonComponent(onClick = { onDismiss() }, modifier = Modifier.weight(0.5f), text = {
                        TitleMediumTextComponent(text = stringResource(id = R.string.cancel), color = MaterialTheme.colorScheme.primary)
                    })
                }
            }
        }
    }
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun ConfirmationBottomSheetComponentPreview() {
    JokerMovieTheme {
        ConfirmationBottomSheetComponent(
            title = "",
            subTitle = "",
            confirmationButtonTitle = "",
            onConfirm = {},
            onDismiss = {},
        )
    }
}
