package com.tuvarna.geo.app

import android.content.Context
import com.tuvarna.geo.rest_api.apis.AuthControllerApi
import com.tuvarna.geo.rest_api.apis.DangerControllerApi
import com.tuvarna.geo.storage.UserSessionStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  private const val BASE_URL = "http://10.0.2.2:8080/api.tuvarna.geo.com/v1"

  @Singleton
  @Provides
  fun provideOkHttpClient(): OkHttpClient {

    return OkHttpClient.Builder().build()
  }

  @Singleton
  @Provides
  fun provideAuthControllerApi(client: OkHttpClient): AuthControllerApi {

    return AuthControllerApi(BASE_URL, client)
  }

  @Singleton
  @Provides
  fun provideDangerControllerApi(client: OkHttpClient): DangerControllerApi {

    return DangerControllerApi(BASE_URL, client)
  }

  @Provides
  @Singleton
  fun provideUserSessionStorage(@ApplicationContext context: Context): UserSessionStorage {
    return UserSessionStorage(context)
  }
}
