package com.bbrustol.core.data.di

import com.bbrustol.core.data.remote.spacex.SpacexDataSource
import com.bbrustol.core.data.remote.spacex.SpacexService
import com.bbrustol.core.data.repository.SpacexRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {

    @Singleton
    @Provides
    fun provideSpacexService(retrofit: Retrofit): SpacexService =
        retrofit.create(SpacexService::class.java)

    @Singleton
    @Provides
    fun provideSpacexDataSource(spacexService: SpacexService): SpacexDataSource =
        SpacexDataSource(spacexService)

    @Singleton
    @Provides
    fun provideSpacexRepository(spacexDataSource: SpacexDataSource): SpacexRepository =
        SpacexRepository(spacexDataSource)

}