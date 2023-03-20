package com.bbrustol.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bbrustol.features.home.compose.CardCompanyInfo
import com.bbrustol.features.home.compose.CardLaunch
import com.bbrustol.features.home.compose.ErrorScreen
import com.bbrustol.features.home.model.HomeModel

@Composable
fun ShowGenericError(message: String, onRetryAction:() -> Unit) {
    ErrorScreen(message = message, onRetryAction)
}

@Composable
fun ShowError(code: Int, message: String?, onRetryAction:() -> Unit) {
    ErrorScreen("code error: $code - $message", onRetryAction )
}

@Composable
fun IsLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LaunchesScreen(homeModel: HomeModel) {
    val state = rememberLazyListState()
    LazyColumn(state = state) {
        item {
            Spacer(modifier = Modifier.height(58.dp))
            CardCompanyInfo(homeModel.companyInfoModel)
        }
        items(homeModel.launchesModel) {
            CardLaunch(it)
        }
    }
}