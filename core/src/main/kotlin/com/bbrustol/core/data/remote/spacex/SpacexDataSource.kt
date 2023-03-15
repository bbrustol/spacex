package com.bbrustol.core.data.remote.spacex

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpacexDataSource @Inject constructor(private val spacexService: SpacexService) {

    suspend fun getCompanyInfo() = spacexService.getCompanyInfo()

    suspend fun getLaunches() = spacexService.getLaunches()
}