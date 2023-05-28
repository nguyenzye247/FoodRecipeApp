package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.interceptor.RequestInterceptor
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.HealthStatusCategoryDetailDao
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BEARER_TOKEN = "Bearer"

val apiModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(RequestInterceptor())
            .callTimeout(7, TimeUnit.SECONDS)
//            .addInterceptor(
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(
                "https://recipe-app-api.herokuapp.com/"
            )
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(RegisterRetrofitService::class.java) }
    single { get<Retrofit>().create(LoginRetrofitService::class.java) }
    single { get<Retrofit>().create(ForgotPasswordService::class.java) }
    single { get<Retrofit>().create(UserInfoService::class.java) }
    single { get<Retrofit>().create(HealthStatusService::class.java) }
    single { get<Retrofit>().create(CategoryService::class.java) }
    single { get<Retrofit>().create(DishPreferredService::class.java) }
    single { get<Retrofit>().create(RecipeService::class.java) }
    //TODO: add more services
}
