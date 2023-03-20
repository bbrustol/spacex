package com.bbrustol.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.bbrustol.core.data.infrastructure.ApiError
import com.bbrustol.core.data.infrastructure.ApiException
import com.bbrustol.core.data.infrastructure.ApiSuccess
import com.bbrustol.core.data.infrastructure.ResourceUtils
import com.bbrustol.core.data.remote.spacex.model.response.company_info.CompanyInfoResponse
import com.bbrustol.core.data.remote.spacex.model.response.launches.LaunchesResponse
import com.bbrustol.core.data.repository.SpacexRepository
import com.bbrustol.features.home.HomeViewModel.*
import com.squareup.moshi.Moshi
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val spacexRepository = mockk<SpacexRepository>()

    private val viewModel = HomeViewModel(spacexRepository)

    private lateinit var moshi: Moshi

    @Before
    fun setUp() {
        moshi = Moshi.Builder().build()
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    private fun getCompanyInfoMock(): CompanyInfoResponse {
        val response = ResourceUtils().openFile("company_info_200.json")
        val adapter = moshi.adapter(CompanyInfoResponse::class.java)
        return adapter.fromJson(response)!!
    }

    private fun getLaunchesMock(): LaunchesResponse {
        val response = ResourceUtils().openFile("launches_200.json")
        val adapter = moshi.adapter(LaunchesResponse::class.java)
        return adapter.fromJson(response)!!
    }


    @Test
    fun `WHEN viewModel is loaded, THEN State going to be an Idle`() = runTest {
        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Idle)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `WHEN fetch getCompanyInfo is requested and receive a throwable before emit, THEN State going to be an Catch`() = runTest {

        coEvery { spacexRepository.getCompanyInfo() } returns flow { throw IllegalStateException() }

        viewModel.fetchCompanyInfo()

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Catch)
            coVerify(exactly = 1) { spacexRepository.getCompanyInfo() }
            coVerify(exactly = 0) { spacexRepository.getLaunches() }
            confirmVerified(spacexRepository)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `WHEN fetch getCompanyInfo is requested unsuccessfully, THEN State going to be an Catch`() = runTest {

        coEvery { spacexRepository.getCompanyInfo() } returns flow { emit(ApiException(Throwable(""))) }

        viewModel.fetchCompanyInfo()

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Catch)
            coVerify(exactly = 1) { spacexRepository.getCompanyInfo() }
            coVerify(exactly = 0) { spacexRepository.getLaunches() }
            confirmVerified(spacexRepository)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `WHEN fetch getCompanyInfo is requested unsuccessfully, THEN State going to be an Failure`() = runTest {

        coEvery { spacexRepository.getCompanyInfo() } returns flow { emit(ApiError(0, "")) }

        viewModel.fetchCompanyInfo()

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Failure)
            coVerify(exactly = 1) { spacexRepository.getCompanyInfo() }
            coVerify(exactly = 0) { spacexRepository.getLaunches() }
            confirmVerified(spacexRepository)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `WHEN fetch getCompanyInfo is requested successfully and getLaunches is requested and receive a throwable before emit, THEN State going to be an Catch`() = runTest {

        coEvery { spacexRepository.getCompanyInfo() } returns flow { emit(ApiSuccess(getCompanyInfoMock())) }
        coEvery { spacexRepository.getLaunches() } returns flow { throw IllegalStateException() }

        viewModel.fetchCompanyInfo()

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Catch)
            coVerify(exactly = 1) { spacexRepository.getCompanyInfo() }
            coVerify(exactly = 1) { spacexRepository.getLaunches() }
            confirmVerified(spacexRepository)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `WHEN fetch getCompanyInfo is requested successfully and getLaunches  is requested unsuccessfully, THEN State going to be an Catch`() = runTest {

        coEvery { spacexRepository.getCompanyInfo() } returns flow { emit(ApiSuccess(getCompanyInfoMock())) }
        coEvery { spacexRepository.getLaunches() } returns flow { emit(ApiException(Throwable(""))) }

        viewModel.fetchCompanyInfo()

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Catch)
            coVerify(exactly = 1) { spacexRepository.getCompanyInfo() }
            coVerify(exactly = 1) { spacexRepository.getLaunches() }
            confirmVerified(spacexRepository)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `WHEN fetch getCompanyInfo is requested successfully and getLaunches is requested unsuccessfully, THEN State going to be an Failure`() = runTest {

        coEvery { spacexRepository.getCompanyInfo() } returns flow { emit(ApiSuccess(getCompanyInfoMock())) }
        coEvery { spacexRepository.getLaunches() } returns flow { emit(ApiError(0, "")) }

        viewModel.fetchCompanyInfo()

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Failure)
            coVerify(exactly = 1) { spacexRepository.getCompanyInfo() }
            coVerify(exactly = 1) { spacexRepository.getLaunches() }
            confirmVerified(spacexRepository)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `WHEN fetch getCompanyInfo and getLaunches are requested successfully, THEN State going to be a Success`() = runTest {

        coEvery { spacexRepository.getCompanyInfo() } returns flow { emit(ApiSuccess(getCompanyInfoMock())) }
        coEvery { spacexRepository.getLaunches() } returns flow { emit(ApiSuccess(listOf(getLaunchesMock()))) }

        viewModel.fetchCompanyInfo()

        viewModel.uiState.test {
            assertTrue(awaitItem() is UiState.Success)
            coVerify(atMost = 1) { spacexRepository.getCompanyInfo() }
            coVerify(atMost = 1) { spacexRepository.getLaunches() }
            confirmVerified(spacexRepository)
            cancelAndConsumeRemainingEvents()
        }
    }
}