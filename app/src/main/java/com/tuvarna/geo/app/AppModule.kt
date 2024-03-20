package com.tuvarna.geo.app

import com.tuvarna.geo.rest_api.apis.RegisterControllerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideOkHttpClient(): OkHttpClient {

    return OkHttpClient.Builder().build()
  }

  @Singleton
  @Provides
  fun provideRegisterControllerApi(client: OkHttpClient): RegisterControllerApi {
    val basePath = "http://10.0.2.2:8080"
    return RegisterControllerApi(basePath, client)
  }
}
