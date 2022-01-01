package com.citizen.registration.di

import android.content.Context
import android.content.SharedPreferences
import com.citizen.registration.data.model.NoNetworkException
import com.citizen.registration.database.SharedPreferenceManager
import com.citizen.registration.network.NetworkHelper
import com.citizen.registration.network.api.ApiHelper
import com.citizen.registration.network.api.ApiHelperImpl
import com.citizen.registration.network.api.ApiServices
import com.citizen.registration.utils.constants.Constants.Companion.PREF_NAME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 1:03 PM
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule
{
    @Provides
    fun provideBaseUrl() = "http://app.azadulbari.tech/"//"https://citizen.fruitsbaazar.com/"//

    @Provides
    @Singleton
    fun provideOkHttpClient(helper: NetworkHelper, prefManager : SharedPreferenceManager): OkHttpClient {
        val interceptor = Interceptor { chain ->

            if(!helper.isNetworkConnected()) throw NoNetworkException()

            val original: Request = chain.request()
            val request: Request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer "+prefManager.getAccessToken())
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiServices(retrofit: Retrofit) : ApiServices = retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl) : ApiHelper = apiHelper

    @Provides
    @Singleton
    fun providePrefManager(@ApplicationContext mContext: Context): SharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPref(preferences : SharedPreferences) = SharedPreferenceManager(preferences)
}