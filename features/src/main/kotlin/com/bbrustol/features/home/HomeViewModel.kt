package com.bbrustol.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbrustol.core.data.infrastructure.ApiError
import com.bbrustol.core.data.infrastructure.ApiException
import com.bbrustol.core.data.infrastructure.ApiSuccess
import com.bbrustol.core.data.remote.spacex.model.response.launches.LaunchesResponse
import com.bbrustol.core.data.repository.SpacexRepository
import com.bbrustol.features.home.HomeViewModel.UiState.*
import com.bbrustol.features.home.model.CompanyInfoModel
import com.bbrustol.features.home.model.HomeModel
import com.bbrustol.features.home.model.LaunchesModel
import com.bbrustol.features.home.model.mappers.toCompanyInfoModel
import com.bbrustol.features.home.model.mappers.toListLaunchModel
import com.bbrustol.uikit.extensions.formatDate
import com.bbrustol.uikit.utils.TimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SpacexRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(Idle)
    var uiState: StateFlow<UiState> = _uiState

    private lateinit var homeOriginalModel: HomeModel

    fun fetchCompanyInfo() = viewModelScope.launch {
        repository.getCompanyInfo()
            .onStart { _uiState.value = Loading }
            .catch { _uiState.value = Catch(it.message) }
            .collect {
                when (it) {
                    is ApiError -> _uiState.value = Failure(it.code, it.message)
                    is ApiException -> _uiState.value = Catch(it.e.message)
                    is ApiSuccess -> fetchLaunches(it.data.toCompanyInfoModel())
                }
            }
    }

    private fun fetchLaunches(companyInfoModel: CompanyInfoModel) = viewModelScope.launch {
        repository.getLaunches()
            .catch { _uiState.value = Catch(it.message) }
            .collect {
                _uiState.value = when (it) {
                    is ApiError -> Failure(it.code, it.message)
                    is ApiException -> Catch(it.e.message)
                    is ApiSuccess -> success(it.data, companyInfoModel)
                }
            }
    }

    private fun success(
        response: List<LaunchesResponse>,
        companyInfoModel: CompanyInfoModel
    ): Success {
        val yearChecked = getYears(response.toListLaunchModel())

        homeOriginalModel = HomeModel(
            companyInfoModel = companyInfoModel,
            launchesModel = response.toListLaunchModel(),
            yearsOriginal = yearChecked,
            yearsChecked = yearChecked
        )

        return Success(homeOriginalModel)
    }

    //region filter
    fun filterList(yearsChecked: Set<String>, isDesc: Boolean) {

        val launchesModelList =
            homeOriginalModel.launchesModel.filter { yearsChecked.contains(checkYear(it.launchDate)) }


        _uiState.value = Success(
            HomeModel(
                homeOriginalModel.companyInfoModel,
                if (isDesc) {
                    launchesModelList.sortedByDescending { it.launchDate }
                } else {
                    launchesModelList.sortedBy { it.launchDate }
                }
,
                homeOriginalModel.yearsOriginal,
                yearsChecked
            )
        )
    }

    private fun checkYear(launchDate: String) = launchDate.formatDate(TimeFormat.YYYY.pattern)

    private fun getYears(launchesModel: List<LaunchesModel>): Set<String> {
        val yearsList = ArrayList<String>()
        launchesModel.forEach {
            yearsList.add(it.launchDate.formatDate(TimeFormat.YYYY.pattern))
        }
        return yearsList.toSet()
    }
    //endregion


    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val homeModel: HomeModel) : UiState()
        data class Failure(val code: Int, val message: String?) : UiState()
        data class Catch(val message: String?) : UiState()
    }
}