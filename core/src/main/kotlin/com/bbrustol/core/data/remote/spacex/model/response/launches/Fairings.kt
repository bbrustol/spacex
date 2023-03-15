package com.bbrustol.core.data.remote.spacex.model.response.launches

data class Fairings(
    val recovered: Boolean?,
    val recovery_attempt: Boolean?,
    val reused: Boolean?,
    val ship: String?
)