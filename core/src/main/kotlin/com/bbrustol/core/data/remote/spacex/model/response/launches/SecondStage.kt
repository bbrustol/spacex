package com.bbrustol.core.data.remote.spacex.model.response.launches

data class SecondStage(
    val block: Int?,
    val payloads: List<Payload>
)