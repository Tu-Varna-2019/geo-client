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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  private const val BASE_URL = "http://10.0.2.2:8080/api.tuvarna.geo.com/v1"

  @Singleton
  @Provides
  fun provideOkHttpClient(userSessionStorage: UserSessionStorage): OkHttpClient {

    return OkHttpClient.Builder().addInterceptor(providerAuthOkHttp(userSessionStorage)).build()
  }

  @Provides
  fun providerAuthOkHttp(userSessionStorage: UserSessionStorage): AuthInterceptor {
    return AuthInterceptor(userSessionStorage)
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

class AuthInterceptor(private val userSessionStorage: UserSessionStorage) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    val accessToken = runBlocking { userSessionStorage.readAccessToken().first() }
    val requestWithAuthHeader =
      originalRequest.newBuilder().header("Authorization", "Bearer $accessToken").build()
    return chain.proceed(requestWithAuthHeader)
  }
}
