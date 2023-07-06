package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.interceptor.RequestInterceptor
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.*
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BEARER_TOKEN = "Bearer"

//val detectionApiModule = module {
//    single {
//        Retrofit.Builder()
//            .client(get<OkHttpClient>())
//            .baseUrl("https://detect.roboflow.com/")
////            .addConverterFactory(ScalarsConverterFactory.create())
//            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    single { get<Retrofit>().create(DetectionService::class.java) }
//}

val apiModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
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
    single { get<Retrofit>().create(CollectionService::class.java) }
    single { get<Retrofit>().create(AuthorService::class.java) }
    single { get<Retrofit>().create(IngredientService::class.java) }
    single { get<Retrofit>().create(SearchFilterService::class.java) }
    single { get<Retrofit>().create(SearchService::class.java) }
    single { get<Retrofit>().create(CalendarService::class.java) }
    single { get<Retrofit>().create(WeeklyPlanService::class.java) }
    single { get<Retrofit>().create(HealthGoalService::class.java) }
    //TODO: add more services
}
