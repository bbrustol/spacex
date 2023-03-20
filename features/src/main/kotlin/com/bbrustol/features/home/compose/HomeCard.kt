package com.bbrustol.features.home.compose

import android.icu.text.NumberFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bbrustol.features.home.compose.utils.CompanyInfoPreviewParamProvider
import com.bbrustol.features.home.compose.utils.LaunchPreviewParamProvider
import com.bbrustol.features.home.model.CompanyInfoModel
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
fun CardLaunch(model: LaunchesModel) {
    val openDialogCustom = remember{ mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 0.dp)
            .clickable { },
        shape = RoundedCornerShape(size = 4.dp),
        colors = getCardColor(model.isLaunchSuccess?: false),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .clickable { openDialogCustom.value = true }
        ) {
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
                        TextsCard(
                            stringResource(UIKIT_R.string.home_mission, model.missionName)
                        )

                        TextsCard(
                            stringResource(
                                UIKIT_R.string.home_datetime,
                                model.launchDate.formatDate(TimeFormat.DDMMYYYY.pattern),
                                model.launchDate.formatDate(TimeFormat.HHMMSS.pattern)
                            )
                        )

                        TextsCard(
                            stringResource(
                                UIKIT_R.string.home_rocket,
                                model.rocketName,
                                model.rocketType
                            )
                        )

                        TextsCard(
                            stringResource(
                                UIKIT_R.string.home_days,
                                stringResource(if (isAfter(model.launchDate)) UIKIT_R.string.days_since else UIKIT_R.string.days_from),
                                model.launchDate.betweenDates()
                            )
                        )
                    }

                    Image(
                        painter = painterResource(
                            id = if (model.isLaunchSuccess == true) {
                                UIKIT_R.drawable.rocket_success
                            } else {
                                UIKIT_R.drawable.rocket_fail
                            }
                        ),
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
    if (openDialogCustom.value) {
        LaunchesDialog(openDialogCustom = openDialogCustom, model)
    }
}
@Composable
fun getCardColor(isLaunchSuccess:Boolean): CardColors =
    cardColors(containerColor = if (isLaunchSuccess) {
    Color.Green.copy(alpha = .4f)
} else {
    Color.Red.copy(alpha = .4f)
})

@Composable
fun CardCompanyInfo(model: CompanyInfoModel) {
    with(model) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
        ) {
            TextsCard(
                stringResource(
                    UIKIT_R.string.home_header,
                    companyName, founder, founded, employeesTotal, launchSites, valuation.currency()
                ), 10,
                SpanStyle(
                    fontWeight = FontWeight.W900,
                    color = Color(0xFF4552B8),
                    fontSize = 18.sp,
                    shadow = Shadow(Color.Black, Offset.Zero, 1.2f)
                )
            )
        }
    }
}

private fun Long.currency(locale: Locale = Locale.US) =
    NumberFormat.getCurrencyInstance(locale).format(this)

@Preview(showBackground = true)
@Composable
fun CardCompanyInfoPreview(
    @PreviewParameter(CompanyInfoPreviewParamProvider::class) companyInfoModel: CompanyInfoModel
) {
    CardCompanyInfo(companyInfoModel)
}

@Preview
@Composable
fun LaunchCardPreview(
    @PreviewParameter(LaunchPreviewParamProvider::class) launchesModel: LaunchesModel
) {
    CardLaunch(launchesModel)
}
