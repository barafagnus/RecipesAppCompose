package ru.vysokov.recipesappcompose.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun ScreenHeader(
    title: String,
    contentDescription: String,
    imageModel: Any?,
    favoritesButton: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .height(Dimens.headerImageHeight)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = imageModel,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.bcg_categories),
            error = painterResource(R.drawable.bcg_categories),
            modifier = Modifier.fillMaxSize()
        )

        if (favoritesButton != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Dimens.paddingMedium)
            ) {
                favoritesButton()
            }
        }

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
        imageModel = R.drawable.bcg_categories,
        favoritesButton = {
            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {}
            ) {
                Icon(
                    painterResource(R.drawable.ic_heart_empty),
                    contentDescription = "",
                )
            }
        }
    )
}