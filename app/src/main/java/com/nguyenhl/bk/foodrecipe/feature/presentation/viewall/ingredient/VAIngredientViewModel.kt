package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient

import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.data.repository.IngredientRepository
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllViewModel

class VAIngredientViewModel(
    val input: BaseInput.BaseViewAllInput.ViewAllIngredientInput,
    private val ingredientRepository: IngredientRepository
) : BaseViewAllViewModel(input){

    init {

    }
}
