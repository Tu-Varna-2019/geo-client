package com.tuvarna.geo.di

import com.tuvarna.geo.apis.RegisterControllerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Singleton
  @Provides
  fun provideRegisterControllerApi(): RegisterControllerApi {

    return RegisterControllerApi()
  }
}
