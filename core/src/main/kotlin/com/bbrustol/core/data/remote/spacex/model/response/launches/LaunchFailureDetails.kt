package com.bbrustol.core.data.remote.spacex.model.response.launches

data class LaunchFailureDetails(
    val altitude: Int,
    val reason: String,
    val time: Int
)