package com.bbrustol.features.home.model

data class HomeModel(
    val companyInfoModel: CompanyInfoModel,
    val launchesModel: List<LaunchesModel>,
    val yearsOriginal:Set<String>,
    val yearsChecked:Set<String>
)