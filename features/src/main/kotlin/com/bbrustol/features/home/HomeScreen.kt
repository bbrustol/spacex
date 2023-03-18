package com.bbrustol.features.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bbrustol.features.R
import com.bbrustol.features.home.HomeViewModel.State
import com.bbrustol.features.home.HomeViewModel.State.*
import com.bbrustol.features.home.compose.CardSample
import com.bbrustol.features.home.model.HomeModel
import com.bbrustol.uikit.theme.SpacexTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    DisposableEffect(key1 = viewModel) {
        viewModel.fetchCompanyInfo()
        onDispose { /*nothing to do*/ }
    }

    RenderState(state)
}

@Composable
fun RenderState(state: State) = when (state) {
    Idle -> isIdle()
    Loading -> isLoading()
    is Failure -> showError(state.code, state.message)
    is Catch -> showGenericError(state.message ?: stringResource(R.string.catch_generic_message))
    is Success -> ShowSuccess(state.homeModel)
}
private fun showGenericError(message: String) { Log.d("UI", "GenericEror - $message") }

private fun showError(code: Int, message: String?) { Log.d("UI", "Error - $code | $message") }

private fun isLoading() { Log.d("UI", "Loding") }

fun isIdle() { Log.d("UI", "Idle") }
@Composable
fun ShowSuccess(homeModel: HomeModel) {
    Log.d("UI", "Success")
    val context = LocalContext.current
    val state = rememberLazyListState()
    LazyColumn(state = state) {
        items(homeModel.launchesModel) {
            CardSample(it) {
                Toast.makeText(context, it.rocketName, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpacexPreview() {
    SpacexTheme {
        HomeScreen()
    }
}
