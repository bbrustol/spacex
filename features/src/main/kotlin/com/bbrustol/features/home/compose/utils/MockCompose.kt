package com.bbrustol.features.home.compose.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bbrustol.features.home.model.CompanyInfoModel
import com.bbrustol.features.home.model.LaunchesModel

class CompanyInfoPreviewParamProvider : PreviewParameterProvider<CompanyInfoModel> {
    override val values: Sequence<CompanyInfoModel> =
        sequenceOf(
            CompanyInfoModel(
                companyName = "BBrustol Ltda",
                founder = "Bruno Brustoloni e Oliveira",
                founded = 3033,
                employeesTotal = 1,
                launchSites = 0,
                valuation = 0
            )
        )
}

class LaunchPreviewParamProvider : PreviewParameterProvider<LaunchesModel> {
    override val values: Sequence<LaunchesModel> =
        sequenceOf(
            LaunchesModel(
                id = 0,
                imageUrl = null,
                missionName = "Beep Beep, Guess who's Coming",
                rocketName = "Turtle",
                rocketType = "the long one",
                launchDate = "3023-01-1T13:50:00.000Z",
                isLaunchSuccess = false,
                articleUrl = "bbrustol.medium.com/how-to-implement-android-accessibility-part-1-general-by-bruno-brustoloni-a46f906ff067",
                wikipediaUrl = "www.wikipedia.org",
                videoUrl = "www.youtube.com/watch?v=PB-hZVTScUg&list=PLWz5rJ2EKKc91i2QT8qfrfKgLNlJiG1z7"
            )
        )
}
