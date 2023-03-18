package com.bbrustol.features.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bbrustol.features.home.model.LaunchesModel
import com.bbrustol.uikit.compose.TextsCard
import com.bbrustol.uikit.extensions.betweenDates
import com.bbrustol.uikit.extensions.formatDate
import com.bbrustol.uikit.extensions.isAfter
import com.bbrustol.uikit.utils.LoadImage
import com.bbrustol.uikit.utils.TimeFormat
import java.util.*
import com.bbrustol.uikit.R as UIKIT_R

@Composable
fun CardSample(model: LaunchesModel, onClickStartSource: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 0.dp)
            .clickable { },
        shape = RoundedCornerShape(size = 4.dp),
        backgroundColor = if (model.isLaunchSuccess == true) { Color.Green.copy(alpha = .4f) } else { Color.Red.copy(alpha = .2f) },
        elevation = 2.dp,
    ) {
        Row(modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClickStartSource)) {

            with(model) {
                Row {

                    LoadImage(
                        imageUrl = imageUrl,
                        contentDescription = null,
                        placeholder = UIKIT_R.drawable.crash_rocket_with_bg,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .weight(.2f),
                        contentScale = ContentScale.FillBounds
                    )

                    Column(
                        modifier = Modifier
                            .padding(4.dp, 0.dp)
                            .align(Alignment.CenterVertically)
                            .weight(.6f)
                    ) {
                        TextsCard(stringResource(UIKIT_R.string.home_mission, model.missionName))

                        TextsCard(
                            stringResource(
                                UIKIT_R.string.home_datetime,
                                model.launchDate.formatDate(TimeFormat.DDMMYYYY.pattern),
                                model.launchDate.formatDate(TimeFormat.HHMMSS.pattern)
                            )
                        )

                        TextsCard(stringResource(UIKIT_R.string.home_rocket, model.rocketName, model.rocketType))

                        TextsCard(
                            stringResource(
                                UIKIT_R.string.home_days,
                                stringResource(if (isAfter(model.launchDate)) UIKIT_R.string.days_since else UIKIT_R.string.days_from),
                                model.launchDate.betweenDates()
                            )
                        )
                    }

                    Image(
                        painter = painterResource(id = if (model.isLaunchSuccess == true) { UIKIT_R.drawable.rocket_success } else { UIKIT_R.drawable.rocket_fail}),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(40.dp)
                            .weight(.1f)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CardSamplePreview(
    @PreviewParameter(ListPreviewParamProvider::class) launchesModel: LaunchesModel
) {
    CardSample(launchesModel) {}
}

private class ListPreviewParamProvider : PreviewParameterProvider<LaunchesModel> {
    override val values: Sequence<LaunchesModel> =
        sequenceOf(
            LaunchesModel(
                id = 0,
                imageUrl = null,
                missionName = "Beep Beep, Guess who's Coming",
                rocketName = "Turtle",
                rocketType = "the long one",
                launchDate = "2023-03-17T03:50:00.000Z",
                isLaunchSuccess = false,
                articleUrl = "www.google.com",
                wikipediaUrl = "www.wikipedia.org",
                videoUrl = ""
            )
        )
}