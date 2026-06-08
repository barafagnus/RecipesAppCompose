package ru.vysokov.recipesappcompose.features.categories.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.vysokov.recipesappcompose.core.ui.RecipeImage
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.features.categories.presentation.model.CategoryUiModel
import ru.vysokov.recipesappcompose.features.categories.presentation.model.toUiModel
import ru.vysokov.recipesappcompose.ui.theme.Dimens
import ru.vysokov.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: CategoryUiModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.cornerRadiusNormal),
        colors = (CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
        onClick = onClick
    ) {
        Column {
            RecipeImage(
                imageUrl = category.imageUrl,
                contentDescription = "category"
            )

            Spacer(modifier = Modifier.height(Dimens.spacerMedium))

            Text(
                modifier = Modifier
                    .padding(horizontal = Dimens.paddingSmall),
                text = category.title.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(Dimens.spacerMedium))

            Text(
                modifier = Modifier
                    .padding(horizontal = Dimens.paddingSmall),
                text = category.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                minLines = 3,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(Dimens.spacerMedium))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryItemPreview() {
    RecipesAppComposeTheme {
        CategoryItem(
            category = RecipesRepositoryStub.getCategories()[0].toUiModel(),
            onClick = {}
        )
    }
}