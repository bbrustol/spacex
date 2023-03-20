package com.bbrustol.spacex

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bbrustol.features.R
import com.bbrustol.features.home.*
import com.bbrustol.features.home.HomeViewModel.*
import com.bbrustol.features.home.HomeViewModel.UiState.*
import com.bbrustol.features.home.model.HomeModel
import com.bbrustol.uikit.compose.FilterChipLayout
import com.bbrustol.uikit.compose.scaffold.TopBar
import com.bbrustol.uikit.theme.SpacexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.bbrustol.spacex.R as UIKIT_R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Start()
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Start(viewModel: HomeViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RenderState(uiState = uiState, viewModel::fetchCompanyInfo, viewModel::filterList)

    DisposableEffect(key1 = viewModel) {
        viewModel.fetchCompanyInfo()
        onDispose { /*nothing to do*/ }
    }
}

@Composable
fun RenderState(uiState: UiState, onRetryAction: () -> Unit, onFilter: (Set<String>, Boolean) -> Unit) {
    when (uiState) {
        Idle -> {/*nothing to do*/}
        Loading -> IsLoading()
        is Failure -> ShowError(uiState.code, uiState.message, onRetryAction)
        is Catch -> ShowGenericError(
            uiState.message ?: stringResource(R.string.catch_generic_message),
            onRetryAction
        )
        is Success -> SetupView(uiState.homeModel, onFilter)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupView(homeModel: HomeModel, onFilter: (Set<String>, Boolean) -> Unit) {
    SpacexTheme {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopBar(onOpenDrawer = {
                    scope.launch {
                        with(drawerState) {
                            if (isClosed) open() else close()
                        }
                    }
                })
            },
        ) {
            SetDrawer(drawerState, homeModel, onFilter)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetDrawer(
    drawerState: DrawerState,
    homeModel: HomeModel,
    onFilter: (Set<String>, Boolean) -> Unit
) {
    val checkedState = remember { mutableStateOf(false)  }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(78.dp),)

                    Text(text = stringResource(UIKIT_R.string.choose_the_dates))

                    FilterChipLayout(homeModel.yearsOriginal, homeModel.yearsChecked) {
                        onFilter(it, checkedState.value)
                    }

                    Spacer(Modifier.height(24.dp),)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(modifier = Modifier.padding(8.dp), text = stringResource(UIKIT_R.string.descending))

                        Switch(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                onFilter(homeModel.yearsChecked, checkedState.value)
                            }
                        )
                    }
                }
            }
        },
        content = { LaunchesScreen(homeModel) }
    )
}
