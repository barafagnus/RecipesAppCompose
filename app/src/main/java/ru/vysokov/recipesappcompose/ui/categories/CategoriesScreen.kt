package ru.vysokov.recipesappcompose.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ScreenHeader(
            title = stringResource(R.string.categories),
            contentDescription = stringResource(R.string.categories),
            imageModel = R.drawable.bcg_categories
        )

        LazyVerticalGrid(
            modifier = Modifier,
            contentPadding = PaddingValues(Dimens.paddingMedium),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
        ) {
            items(items = RecipesRepositoryStub.getCategories()) { item ->
                CategoryItem(
                    category = item.toUiModel(),
                    onClick = { onCategoryClick(item.id) }
                )
            }
        }
    }
}