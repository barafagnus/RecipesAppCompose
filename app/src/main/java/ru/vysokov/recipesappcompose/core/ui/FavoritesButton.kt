package ru.vysokov.recipesappcompose.core.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun FavoritesButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(Dimens.favoritesIconSize),
            painter = if (isFavorite) painterResource(R.drawable.ic_heart) else painterResource(R.drawable.ic_heart_empty),
            contentDescription = "",
            tint = Color.Unspecified
        )
    }
}