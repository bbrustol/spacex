package com.bbrustol.features.home.model.mappers

import com.bbrustol.core.data.remote.spacex.model.response.launches.LaunchesResponse
import com.bbrustol.features.home.model.LaunchesModel

fun List<LaunchesResponse>.toListLaunchModel() = map { it.toLaunchesModel() }

fun LaunchesResponse.toLaunchesModel() =
    LaunchesModel(
        id = flight_number,
        imageUrl = links.mission_patch_small,
        missionName = mission_name,
        rocketName = rocket.rocket_name,
        rocketType = rocket.rocket_type,
        launchDate = launch_date_utc,
        isLaunchSuccess = launch_success,
        articleUrl = links.article_link,
        wikipediaUrl = links.wikipedia,
        videoUrl = links.video_link
    )