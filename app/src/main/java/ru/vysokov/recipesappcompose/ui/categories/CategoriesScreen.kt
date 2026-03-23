package ru.vysokov.recipesappcompose.ui.categories

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
import ru.vysokov.recipesappcompose.ui.components.ScreenHeader
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun CategoriesScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
    ) {
        ScreenHeader(
            title = "Категории",
            contentDescription = "Категории",
            imagePainter = painterResource(R.drawable.img_categories)
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
        ) {
            item { Text(text = "Категория") }
        }
    }
}