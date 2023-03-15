package com.bbrustol.features.home.model

data class LaunchesModel(
    val id: Int,
    val imageUrl: String?,
    val missionName: String,
    val rocketName: String,
    val rocketType: String,
    val launchDate: String,
    val isLaunchSuccess: Boolean?,
    val articleUrl: String?,
    val wikipediaUrl: String?,
    val videoUrl: String?
)