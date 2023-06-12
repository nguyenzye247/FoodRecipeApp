package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.forgot.ForgotPasswordViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.login.LoginViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.register.RegisterViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered.DishPreferredViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.createinfo.CreateInfoViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.chef.ChefDetailViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.collection.CollectionDetailViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient.IngredientDetailViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.splash.SplashViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.chef.VAChefViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.collection.VACollectionViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient.VAIngredientActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient.VAIngredientViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.random.VARandomRecipeViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.suggest.VASuggestViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (input: BaseInput.RegisterInput) ->
        ViewModelProviderFactory(input).create(RegisterViewModel::class.java)
    }

    viewModel { (input: BaseInput.LoginInput) ->
        ViewModelProviderFactory(input).create(LoginViewModel::class.java)
    }

    viewModel { (input: BaseInput.ForgotPasswordInput) ->
        ViewModelProviderFactory(input).create(ForgotPasswordViewModel::class.java)
    }

    viewModel { (input: BaseInput.CreateInfoInput) ->
        ViewModelProviderFactory(input).create(CreateInfoViewModel::class.java)
    }

    viewModel { (input: BaseInput.DishPreferredInput) ->
        ViewModelProviderFactory(input).create(DishPreferredViewModel::class.java)
    }

    viewModel { (input: BaseInput.MainInput) ->
        ViewModelProviderFactory(input).create(MainViewModel::class.java)
    }

    viewModel { (input: BaseInput.SplashInput) ->
        ViewModelProviderFactory(input).create(SplashViewModel::class.java)
    }

    viewModel { (input: BaseInput.BaseViewAllInput.ViewAllCollectionInput) ->
        ViewModelProviderFactory(input).create(VACollectionViewModel::class.java)
    }

    viewModel { (input: BaseInput.BaseViewAllInput.ViewAllSuggestInput) ->
        ViewModelProviderFactory(input).create(VASuggestViewModel::class.java)
    }

    viewModel { (input: BaseInput.BaseViewAllInput.ViewAllRandomInput) ->
        ViewModelProviderFactory(input).create(VARandomRecipeViewModel::class.java)
    }

    viewModel { (input: BaseInput.BaseViewAllInput.ViewAllTopChefInput) ->
        ViewModelProviderFactory(input).create(VAChefViewModel::class.java)
    }

    viewModel { (input: BaseInput.BaseViewAllInput.ViewAllIngredientInput) ->
        ViewModelProviderFactory(input).create(VAIngredientViewModel::class.java)
    }

    viewModel { (input: BaseInput.CollectionDetailInput) ->
        ViewModelProviderFactory(input).create(CollectionDetailViewModel::class.java)
    }

    viewModel { (input: BaseInput.ChefDetailInput) ->
        ViewModelProviderFactory(input).create(ChefDetailViewModel::class.java)
    }

    viewModel { (input: BaseInput.IngredientDetailInput) ->
        ViewModelProviderFactory(input).create(IngredientDetailViewModel::class.java)
    }

    viewModel { (input: BaseInput.RecipeDetailInput) ->
        ViewModelProviderFactory(input).create(RecipeDetailViewModel::class.java)
    }
}