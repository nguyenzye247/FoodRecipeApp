package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityIngredientDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.wajahatkarim3.easyvalidation.core.collection_ktx.textEqualToList
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class IngredientDetailActivity :
    BaseActivity<ActivityIngredientDetailBinding, IngredientDetailViewModel>() {
    private val ingredientInfo by lazy { intent.parcelableExtra<IngredientDto>(KEY_INGREDIENT_DTO) }

    override fun getLazyBinding() = lazy { ActivityIngredientDetailBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<IngredientDetailViewModel> {
        parametersOf(
            BaseInput.IngredientDetailInput(
                application,
                intent.parcelableExtra(KEY_INGREDIENT_DTO)
            )
        )
    }

    override fun initViews() {
        adjustScreenSize(binding.btnBack)
        showLoadingView(true)
        binding.apply {
            ingredientInfo?.let {

            }
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }

        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(getIngredientDetail()) { ingredient ->
                ingredient?.let {
                    bindIngredientDetailDataView(it)
                }
                showLoadingView(false)
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun bindIngredientDetailDataView(ingredientDetailDto: IngredientDetailDto) {
        binding.apply {
            ivIngredientThumbnail.loadImage(ingredientDetailDto.imageUrl)
            tvIngredientName.text = ingredientDetailDto.name
            val spellText = "|${ingredientDetailDto.pronunciation}|"
            tvIngredientSpell.text = spellText

            contentLayout.apply {
                tvIngredientInfo.text = ingredientDetailDto.info
                tvIngredientTitle.text = ingredientDetailDto.title
                tvIngredientDescription.text = ingredientDetailDto.description.joinToString(separator = "\n")
            }
        }
    }

    private fun showEmptyView() {

    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    companion object {
        const val KEY_INGREDIENT_DTO = "key_ingredient_dto"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<IngredientDetailActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
