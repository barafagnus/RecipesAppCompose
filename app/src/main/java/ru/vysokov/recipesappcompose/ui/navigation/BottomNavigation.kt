package ru.vysokov.recipesappcompose.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = Dimens.paddingMedium,
                vertical = Dimens.paddingSmall
            )
            .navigationBarsPadding()
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(Dimens.buttonHeight),

            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            shape = RoundedCornerShape(Dimens.cornerRadiusNormal),
            onClick = { onCategoriesClick() }
        ) {
            Text(
                text = stringResource(R.string.categories_button),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surface
            )
        }

        Spacer(modifier = Modifier.width(Dimens.buttonHorizontalSpacing))

        Button(
            modifier = Modifier
                .weight(1f)
                .height(Dimens.buttonHeight),

            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(Dimens.cornerRadiusNormal),
            onClick = { onFavoritesClick() }
        ) {
            Text(
                text = stringResource(R.string.favorites_button),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.width(Dimens.spacerLarge))

            Icon(
                painter = painterResource(R.drawable.ic_heart_empty),
                tint = Color.White,
                contentDescription = stringResource(R.string.favorites_button),
                modifier = Modifier.size(Dimens.iconMediumSize),
            )
        }
    }

}