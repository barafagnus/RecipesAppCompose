package ru.vysokov.recipesappcompose.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun InstructionsList(
    modifier: Modifier = Modifier,
    instructions: List<String>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Способ приготовления".uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(Dimens.paddingMedium))

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.cornerRadiusNormal))
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = Dimens.spacerSmall),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacerSmall)
        ) {
            instructions.forEachIndexed { index, method ->
                InstructionItem(
                    methodRow = method
                )
                if (index < instructions.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = Dimens.listItemPadding),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }

}

@Composable
fun InstructionItem(
    modifier: Modifier = Modifier,
    methodRow: String
) {
    Row(
        modifier = modifier.padding(
            horizontal = Dimens.listItemPadding,
            vertical = Dimens.toDividerSpacing
        ),
    ) {
        Text(
            text = methodRow,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}