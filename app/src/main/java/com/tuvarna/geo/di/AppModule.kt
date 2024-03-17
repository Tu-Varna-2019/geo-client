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
    // If your RegisterControllerApi doesn't need a custom OkHttpClient,
    // just return a new instance of it here
    return RegisterControllerApi()
  }
}
