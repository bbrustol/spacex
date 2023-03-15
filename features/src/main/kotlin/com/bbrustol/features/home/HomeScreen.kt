package com.bbrustol.features.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bbrustol.features.home.model.CompanyInfoModel
import com.bbrustol.features.home.HomeViewModel.*
import com.bbrustol.features.home.HomeViewModel.State.*
import com.bbrustol.uikit.theme.SpacexTheme
import com.bbrustol.uikit.utils.LoadImage
import com.bbrustol.uikit.extensions.card
import com.bbrustol.features.R
import com.bbrustol.features.home.model.LaunchesItemModel
import com.bbrustol.features.home.model.HomeModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
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
    is Loading -> isLoading(state.flag)
    is Failure -> showError(state.code, state.message)
    is Catch -> showGenericError(state.message ?: stringResource(R.string.catch_generic_message))
    is Success -> ShowSuccess(state.homeModel)
}

@Composable
fun ShowSuccess(homeModel: HomeModel) {

    val state = rememberLazyListState()
    LazyColumn(state = state) {
        items(homeModel.launchesModel) { launchesItemModel ->
            ArticleItem(homeModel.companyInfoModel, launchesItemModel)
        }
    }
}

@Composable
fun ArticleItem(companyInfoModel: CompanyInfoModel, launchesItemModel: LaunchesItemModel) {
    with(launchesItemModel) {
        Card(
            elevation = dimensionResource(id = R.dimen.card_elevation),
            shape = MaterialTheme.shapes.card,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.card_side_margin))
                .padding(bottom = dimensionResource(id = R.dimen.card_bottom_margin))
        ) {
            Column(Modifier.fillMaxWidth()) {
                LoadImage(
                    imageUrl = imageUrl,
                    contentDescription = stringResource(R.string.image_default_accessibility),
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = rocketName,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.margin_normal))
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

private fun showGenericError(message: String) {
    Log.d("Api", "GenericEror - $message")
}

private fun showError(code: Int, message: String?) {
    Log.d("Api", "Error - $code | $message")
}

private fun isLoading(flag: Boolean) {
    Log.d("Api", "Loding - $flag")
}

@Preview(showBackground = true)
@Composable
fun SpacexPreview() {
    SpacexTheme {
        HomeScreen()
    }
}
