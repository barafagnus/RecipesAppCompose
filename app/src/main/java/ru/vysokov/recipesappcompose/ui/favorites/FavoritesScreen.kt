package ru.vysokov.recipesappcompose.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun FavoritesScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
    ) {
        ScreenHeader(
            title = "Избранное",
            contentDescription = "Избранное",
            imagePainter = painterResource(R.drawable.bcg_favorites)
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
        ) {
            item { Text(text = "Избранные рецепты") }
        }
    }
}