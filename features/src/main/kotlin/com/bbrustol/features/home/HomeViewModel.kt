package com.bbrustol.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbrustol.core.data.infrastructure.*
import com.bbrustol.core.data.repository.SpacexRepository
import com.bbrustol.features.home.HomeViewModel.State.*
import com.bbrustol.features.home.model.CompanyInfoModel
import com.bbrustol.features.home.model.HomeModel
import com.bbrustol.features.home.model.mappers.toCompanyInfoModel
import com.bbrustol.features.home.model.mappers.toListLaunchModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SpacexRepository
) : ViewModel() {


    private val _state = MutableStateFlow<State>(Loading(false))
    var state: StateFlow<State> = _state

    fun fetchCompanyInfo() = viewModelScope.launch {
        repository.getCompanyInfo()
            .onStart { _state.value = Loading(true) }
            .collect {
                when (it) {
                    is ApiError -> _state.value = Failure(it.code, it.message)
                    is ApiException -> _state.value = Catch(it.e.message)
                    is ApiSuccess -> fetchLaunches(it.data.toCompanyInfoModel())
                }
            }
    }

    private fun fetchLaunches(companyInfoModel: CompanyInfoModel) = viewModelScope.launch {
        repository.getLaunches()
            .onStart { _state.value = Loading(true) }
            .collect {
                _state.value = when (it) {
                    is ApiError -> Failure(it.code, it.message)
                    is ApiException -> Catch(it.e.message)
                    //FIXME(erro de parse)
                    is ApiSuccess -> Success(HomeModel(companyInfoModel, it.data.toListLaunchModel()))
                }
            }
    }

    sealed class State {
        data class Loading(val flag: Boolean = false) : State()
        data class Success(val homeModel: HomeModel) : State()
        data class Failure(val code: Int, val message: String?) : State()
        data class Catch(val message: String?) : State()
    }
}