package com.bbrustol.features.home.model

data class CompanyInfoModel(
    val companyName: String,
    val founder: String,
    val founded: Int,
    val employeesTotal: Int,
    val launchSites: Int,
    val valuation: Long
)