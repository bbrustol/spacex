package com.bbrustol.core.data.remote.spacex

import com.bbrustol.core.data.remote.spacex.model.response.company_info.CompanyInfoResponse
import com.bbrustol.core.data.remote.spacex.model.response.launches.LaunchesResponse
import retrofit2.Response
import retrofit2.http.GET

interface SpacexService {
    @GET("info")
    suspend fun getCompanyInfo(): Response<CompanyInfoResponse>

    @GET("launches")
    suspend fun getLaunches(): Response<List<LaunchesResponse>>

}