package ru.vysokov.recipesappcompose.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun ScreenHeader(
    title: String,
    contentDescription: String,
    imagePainter: Painter,
) {
    Box(
        modifier = Modifier
            .height(Dimens.headerImageHeight)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = imagePainter,
            contentDescription = contentDescription,
        )
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimens.paddingMedium),
            shape = RoundedCornerShape(Dimens.cornerRadiusNormal)
        ) {
            Text(
                modifier = Modifier.padding(Dimens.innerPadding),
                text = title.uppercase(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,

            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ScreenHeaderPreview() {
    ScreenHeader(
        title = "Категории",
        contentDescription = "Категории",
        imagePainter = painterResource(R.drawable.bcg_categories)
    )
}