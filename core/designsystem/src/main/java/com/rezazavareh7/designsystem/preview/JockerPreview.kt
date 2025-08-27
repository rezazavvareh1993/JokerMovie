package com.rezazavareh7.designsystem.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Small Phone",
    device = "spec:width=320dp,height=640dp",
    showBackground = true,
    fontScale = 0.85f,
    locale = "fa",
)
@Preview(
    name = "Normal Phone",
    device = "spec:width=360dp,height=720dp",
    showBackground = true,
    fontScale = 1.0f,
    locale = "fa",
)
@Preview(
    name = "Large Phone",
    device = "spec:width=411dp,height=891dp",
    showBackground = true,
    fontScale = 1.15f,
    locale = "fa",
)
@Preview(
    name = "XL Phone",
    device = "spec:width=480dp,height=960dp",
    showBackground = true,
    fontScale = 1.3f,
    locale = "fa",
)
annotation class JockerPreview
