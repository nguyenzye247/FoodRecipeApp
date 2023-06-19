package com.nguyenhl.bk.foodrecipe.feature.presentation.search

import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

interface SearchInteractionListener {
    fun onSelectRecipe(recipe: RecipeDto)
    fun onFavoriteRecipe(recipe: RecipeDto)
}
