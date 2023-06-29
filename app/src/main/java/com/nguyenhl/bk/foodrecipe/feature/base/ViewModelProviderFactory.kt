package com.nguyenhl.bk.foodrecipe.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyenhl.bk.foodrecipe.feature.data.repository.*
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.ForgotPasswordRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.LoginRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.RegisterRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.search.SearchFilterRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.search.SearchRepository
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.createaccount.CreateAccountViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.createinfo.CreateInfoViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.forgot.ForgotPasswordViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.login.LoginViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.register.RegisterViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered.DishPreferredViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.chef.ChefDetailViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.collection.CollectionDetailViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient.IngredientDetailViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detection.DetectionViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.editprofile.EditProfileViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeFetchRecipeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchFilterUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchMealUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.settings.SettingsViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.splash.SplashViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.chef.VAChefViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.collection.VACollectionViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient.VAIngredientViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.random.VARandomRecipeViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.suggest.VASuggestViewModel
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
    private val recipeRepository: RecipeRepository by inject()
    private val authorRepository: AuthorRepository by inject()
    private val ingredientRepository: IngredientRepository by inject()
    private val searchRepository: SearchRepository by inject()
    private val detectionRepository: DetectionRepository by inject()
    private val searchFilterRepository: SearchFilterRepository by inject()
    private val categoryRepository: CategoryRepository by inject()

    private val homeUseCase: HomeUseCase by inject()
    private val homeFetchRecipeUseCase: HomeFetchRecipeUseCase by inject()
    private val searchFilterUseCase: SearchFilterUseCase by inject()
    private val searchUseCase: SearchUseCase by inject()
    private val searchMealUseCase: SearchMealUseCase by inject()

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
                    homeFetchRecipeUseCase,
                    searchMealUseCase
                ) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(
                    input as BaseInput.LoginInput,
                    loginRepository,
                    userInfoRepository,
                    healthStatusRepository,
                    dishPreferredRepository,
                    userRepository
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

            modelClass.isAssignableFrom(VACollectionViewModel::class.java) -> {
                return VACollectionViewModel(
                    input as BaseInput.BaseViewAllInput.ViewAllCollectionInput,
                    collectionRepository
                ) as T
            }

            modelClass.isAssignableFrom(VASuggestViewModel::class.java) -> {
                return VASuggestViewModel(
                    input as BaseInput.BaseViewAllInput.ViewAllSuggestInput,
                    userInfoRepository,
                    searchRepository,
                    recipeRepository
                ) as T
            }

            modelClass.isAssignableFrom(VARandomRecipeViewModel::class.java) -> {
                return VARandomRecipeViewModel(
                    input as BaseInput.BaseViewAllInput.ViewAllRandomInput,
                    recipeRepository
                ) as T
            }

            modelClass.isAssignableFrom(VAChefViewModel::class.java) -> {
                return VAChefViewModel(
                    input as BaseInput.BaseViewAllInput.ViewAllTopChefInput,
                    authorRepository
                ) as T
            }

            modelClass.isAssignableFrom(VAIngredientViewModel::class.java) -> {
                return VAIngredientViewModel(
                    input as BaseInput.BaseViewAllInput.ViewAllIngredientInput,
                    ingredientRepository
                ) as T
            }

            modelClass.isAssignableFrom(CollectionDetailViewModel::class.java) -> {
                return CollectionDetailViewModel(
                    input as BaseInput.CollectionDetailInput,
                    recipeRepository
                ) as T
            }

            modelClass.isAssignableFrom(ChefDetailViewModel::class.java) -> {
                return ChefDetailViewModel(
                    input as BaseInput.ChefDetailInput,
                    recipeRepository
                ) as T
            }

            modelClass.isAssignableFrom(IngredientDetailViewModel::class.java) -> {
                return IngredientDetailViewModel(
                    input as BaseInput.IngredientDetailInput,
                    ingredientRepository
                ) as T
            }

            modelClass.isAssignableFrom(RecipeDetailViewModel::class.java) -> {
                return RecipeDetailViewModel(
                    input as BaseInput.RecipeDetailInput,
                    recipeRepository
                ) as T
            }

            modelClass.isAssignableFrom(CookingViewModel::class.java) -> {
                return CookingViewModel(
                    input as BaseInput.CookingInput
                ) as T
            }

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                return SearchViewModel(
                    input as BaseInput.SearchInput,
                    searchUseCase,
                    searchFilterUseCase,
                    searchMealUseCase
                ) as T
            }

            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                return EditProfileViewModel(
                    input as BaseInput.EditProfileInput,
                    userInfoRepository,
                    healthStatusRepository
                ) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                return SettingsViewModel(
                    input as BaseInput.SettingsInput
                ) as T
            }

            modelClass.isAssignableFrom(DetectionViewModel::class.java) -> {
                return DetectionViewModel(
                    input as BaseInput.DetectionInput,
                    detectionRepository
                ) as T
            }

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
