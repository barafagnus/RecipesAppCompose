package ru.vysokov.recipesappcompose.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.features.categories.presentation.CategoriesViewModel
import ru.vysokov.recipesappcompose.features.categories.presentation.model.toUiModel
import ru.vysokov.recipesappcompose.features.categories.ui.component.CategoryItem
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String, String) -> Unit
) {
    val viewModel: CategoriesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ScreenHeader(
            title = stringResource(R.string.categories),
            contentDescription = stringResource(R.string.categories),
            imageModel = R.drawable.bcg_categories
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState.errorMessage != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Ошибка загрузки: ${uiState.errorMessage}")
            }
        } else if (uiState.categories.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier,
                contentPadding = PaddingValues(Dimens.paddingMedium),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
            ) {
                items(items = uiState.categories) { item ->
                    CategoryItem(
                        category = item,
                        onClick = { onCategoryClick(item.id, item.title, item.imageUrl) }
                    )
                }
            }
        }

    }
}