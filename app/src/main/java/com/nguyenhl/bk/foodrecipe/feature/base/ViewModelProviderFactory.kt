package com.nguyenhl.bk.foodrecipe.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyenhl.bk.foodrecipe.feature.data.repository.*
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.ForgotPasswordRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.LoginRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.RegisterRepository
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.createaccount.CreateAccountViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.createinfo.CreateInfoViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.forgot.ForgotPasswordViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.login.LoginViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.register.RegisterViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered.DishPreferredViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeFetchRecipeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.splash.SplashViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.collection.ViewAllCollectionViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class ViewModelProviderFactory(private val input: BaseInput) : ViewModelProvider.Factory,
    KoinComponent {
    private val registerRepository: RegisterRepository by inject()
    private val loginRepository: LoginRepository by inject()
    private val userInfoRepository: UserInfoRepository by inject()
    private val userRepository: UserRepository by inject()
    private val healthStatusRepository: HealthStatusRepository by inject()
    private val dishPreferredRepository: DishPreferredRepository by inject()
    private val forgotPasswordRepository: ForgotPasswordRepository by inject()
    private val collectionRepository: CollectionRepository by inject()
    private val categoryRepository: CategoryRepository by inject()

    private val homeUseCase: HomeUseCase by inject()
    private val homeFetchRecipeUseCase: HomeFetchRecipeUseCase by inject()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                return SplashViewModel(
                    input as BaseInput.SplashInput,
                    healthStatusRepository,
                    dishPreferredRepository,
                    userRepository,
                    userInfoRepository
                ) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(
                    input as BaseInput.MainInput,
                    homeUseCase,
                    homeFetchRecipeUseCase
                ) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(
                    input as BaseInput.LoginInput,
                    loginRepository,
                    userInfoRepository
                ) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(
                    input as BaseInput.RegisterInput,
                    registerRepository
                ) as T
            }

            modelClass.isAssignableFrom(CreateAccountViewModel::class.java) -> {
                return CreateAccountViewModel(input as BaseInput.CreateAccountInput) as T
            }

            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> {
                return ForgotPasswordViewModel(
                    input as BaseInput.ForgotPasswordInput,
                    forgotPasswordRepository
                ) as T
            }

            modelClass.isAssignableFrom(DishPreferredViewModel::class.java) -> {
                return DishPreferredViewModel(
                    input as BaseInput.DishPreferredInput,
                    dishPreferredRepository
                ) as T
            }

            modelClass.isAssignableFrom(CreateInfoViewModel::class.java) -> {
                return CreateInfoViewModel(
                    input as BaseInput.CreateInfoInput,
                    userInfoRepository,
                    healthStatusRepository
                ) as T
            }

            modelClass.isAssignableFrom(ViewAllCollectionViewModel::class.java) -> {
                return ViewAllCollectionViewModel(
                    input as BaseInput.BaseViewAllInput.ViewAllCollectionInput,
                    collectionRepository
                ) as T
            }

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
