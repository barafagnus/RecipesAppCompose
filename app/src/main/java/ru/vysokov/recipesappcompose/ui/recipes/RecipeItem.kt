package ru.vysokov.recipesappcompose.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.ui.theme.Dimens
import ru.vysokov.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    model: RecipeUiModel,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.cornerRadiusNormal),
        colors = (CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
        onClick = { onClick(model.id) }
    ) {
        Column() {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.recipeCardImageWidth),
                contentScale = ContentScale.Crop,
                model = model.imageUrl,
                contentDescription = "",
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error)
            )

            Text(
                modifier = Modifier
                    .padding(Dimens.paddingSmall),
                text = model.title.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RecipeItemPreview() {
    RecipesAppComposeTheme {
        val stub by remember {
            mutableStateOf(RecipesRepositoryStub.getRecipesByCategoryId(0)[0].toUiModel())
        }
        RecipeItem(
            model = RecipeUiModel(
                id = stub.id,
                title = stub.title,
                ingredients = stub.ingredients,
                method = stub.method,
                imageUrl = stub.imageUrl
            ),
            onClick = {}
        )
    }
}