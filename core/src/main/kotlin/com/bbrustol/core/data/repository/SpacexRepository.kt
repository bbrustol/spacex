package com.bbrustol.core.data.repository

import com.bbrustol.core.data.infrastructure.ApiResult
import com.bbrustol.core.data.infrastructure.handleApi
import com.bbrustol.core.data.remote.spacex.SpacexDataSource
import com.bbrustol.core.data.remote.spacex.model.response.company_info.CompanyInfoResponse
import com.bbrustol.core.data.remote.spacex.model.response.launches.LaunchesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpacexRepository @Inject constructor(private val remoteSpacexDataSource: SpacexDataSource)  {
    private fun dispatcher() = Dispatchers.IO

    suspend fun getCompanyInfo(): Flow<ApiResult<CompanyInfoResponse>> {
        return flow {
            emit(handleApi { remoteSpacexDataSource.getCompanyInfo() })
        }.flowOn(dispatcher())
    }

    suspend fun getLaunches(): Flow<ApiResult<LaunchesResponse>> {
        return flow {
            emit(handleApi { remoteSpacexDataSource.getLaunches() })
        }.flowOn(dispatcher())
    }
}