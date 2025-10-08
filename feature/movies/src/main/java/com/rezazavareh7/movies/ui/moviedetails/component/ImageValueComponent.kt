package com.rezazavareh7.movies.ui.moviedetails.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.preview.JockerPreview

@Composable
fun ImageValueComponent(
    modifier: Modifier = Modifier,
    image: Int,
    value: String,
) {
    Spacer(Modifier.height(8.dp))
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconComponent(drawableId = image, iconSize = 20.dp)
        Spacer(modifier = Modifier.width(4.dp))
        BodyMediumTextComponent(text = value)
    }
}

@JockerPreview
@Composable
private fun ImageValueComponentPreview() {
    ImageValueComponent(image = android.R.drawable.ic_delete, value = "value")
}
