package ru.vysokov.recipesappcompose.core.ui

import android.R.attr.label
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun FavoritesButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Crossfade(
            targetState = isFavorite,
            animationSpec = tween(durationMillis = 300),
            label = "favorite_animation"
        ) { isCurrentlyFavorite ->

            val heartIcon = rememberVectorPainter(
                image = ImageVector.vectorResource(
                    id = if (isCurrentlyFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
                )
            )

            Icon(
                modifier = Modifier.size(Dimens.iconSize),
                painter = heartIcon,
                contentDescription = "Favorite",
                tint = Color.Unspecified
            )
        }
    }
}