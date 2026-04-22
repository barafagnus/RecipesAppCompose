package ru.vysokov.recipesappcompose.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.vysokov.recipesappcompose.ui.recipes.IngredientsUiModel
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun IngredientsList(
    modifier: Modifier = Modifier,
    scaledIngredients: List<IngredientsUiModel>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cornerRadiusNormal))
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = Dimens.spacerSmall),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacerSmall)
    ) {

        scaledIngredients.forEachIndexed { index, ingredientDto ->
            IngredientItem(ingredient = ingredientDto)
            if (index < scaledIngredients.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = Dimens.listItemPadding),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

    }
}

@Composable
private fun IngredientItem(
    modifier: Modifier = Modifier,
    ingredient: IngredientsUiModel
) {
    Row(
        modifier = modifier
            .padding(horizontal = Dimens.listItemPadding, vertical = Dimens.toDividerSpacing),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier.weight(1f),
            softWrap = true,
            text = ingredient.name.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.width(Dimens.paddingSmall))

        Text(
            text = "${ingredient.amount.uppercase()} ${ingredient.unitOfMeasure.uppercase()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            maxLines = 1
        )
    }
}