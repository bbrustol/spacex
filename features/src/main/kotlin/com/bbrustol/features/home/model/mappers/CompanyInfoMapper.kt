package com.bbrustol.features.home.model.mappers

import com.bbrustol.core.data.remote.spacex.model.response.company_info.CompanyInfoResponse
import com.bbrustol.features.home.model.CompanyInfoModel

fun CompanyInfoResponse.toCompanyInfoModel() =
    CompanyInfoModel(
        companyName = name,
        founder = founder,
        founded = founded,
        employeesTotal = employees,
        launchSites = launch_sites,
        valuation = valuation
    )
