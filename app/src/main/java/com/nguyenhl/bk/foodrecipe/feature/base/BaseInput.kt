package com.nguyenhl.bk.foodrecipe.feature.base

import android.app.Application
import com.nguyenhl.bk.foodrecipe.App
import com.nguyenhl.bk.foodrecipe.feature.dto.*

sealed class BaseInput {

    object NoInput : BaseInput()

    data class SplashInput(
        val application: Application
    ) : BaseInput()

    data class MainInput(
        val application: Application
    ) : BaseInput()

    data class LoginInput(
        val application: Application
    ) : BaseInput()

    data class RegisterInput(
        val application: Application
    ) : BaseInput()

    data class ForgotPasswordInput(
        val application: Application
    ) : BaseInput()

    data class CreateAccountInput(
        val application: Application
    ) : BaseInput()

    data class DishPreferredInput(
        val application: Application
    ) : BaseInput()

    data class CreateInfoInput(
        val application: Application
    ) : BaseInput()

    sealed class BaseViewAllInput(
        val baseApplication: Application
    ) : BaseInput() {
        data class ViewAllCollectionInput(
            val application: Application
        ): BaseViewAllInput(application)

        data class ViewAllIngredientInput(
            val application: Application
        ): BaseViewAllInput(application)

        data class ViewAllRandomInput(
            val application: Application,
            val userInfo: UserInfoDto?
        ): BaseViewAllInput(application)

        data class ViewAllSuggestInput(
            val application: Application,
            val userInfo: UserInfoDto?
        ): BaseViewAllInput(application)
        
        data class ViewAllTopChefInput(
            val application: Application
        ): BaseViewAllInput(application)
    }

    data class CollectionDetailInput(
        val application: Application,
        val collectionDto: CollectionDto?
    ): BaseInput()

    data class ChefDetailInput(
        val application: Application,
        val authorDto: AuthorDto?
    ): BaseInput()

    data class IngredientDetailInput(
        val application: Application,
        val ingredientDto: IngredientDto?
    ): BaseInput()

    data class RecipeDetailInput(
        val application: Application,
        val recipeDto: RecipeDto?
    ): BaseInput()
}
