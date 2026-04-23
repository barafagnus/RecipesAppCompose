package ru.vysokov.recipesappcompose.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.ui.theme.Dimens
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortionsSelector(
    modifier: Modifier = Modifier,
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit
) {
    val sliderColors = SliderDefaults.colors(
        thumbColor = MaterialTheme.colorScheme.tertiary,
        activeTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
        inactiveTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
        activeTickColor = Color.Transparent,
        inactiveTickColor = Color.Transparent
    )
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.ingredients).uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(Dimens.spacerMid))

        Text(
            text = "Порции: $currentPortions",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(Dimens.spacerMid))

        Slider(
            value = currentPortions.toFloat(),
            onValueChange = { onPortionsChange(it.roundToInt()) },
            valueRange = 1f..12f,
            steps = 10,
            colors = sliderColors,
            thumb = {
                Box(
                    modifier = Modifier
                        .width(Dimens.sliderLeverWidth)
                        .height(Dimens.sliderLeverHeight)
                        .background(
                            MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(Dimens.cornerRadiusSmall)
                        )
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    modifier = Modifier
                        .height(Dimens.sliderTrackHeight),
                    sliderState = sliderState,
                    thumbTrackGapSize = 0.dp,
                    colors = sliderColors,
                )

            }
        )
    }
}