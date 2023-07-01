package com.nguyenhl.bk.foodrecipe.feature.presentation.detection.result

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.BottomsheetDetectResultBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detection.DetectionViewModel

class DetectionResultBottomSheet : BottomSheetDialogFragment() {
    private val binding by lazy { BottomsheetDetectResultBinding.inflate(layoutInflater) }
    private val viewModel by lazy { activityViewModels<DetectionViewModel>().value }
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var detectionResultAdapter: DetectionResultAdapter

    private lateinit var onClickListener: DetectResultBottomSheetListener

    private var ingredientResults: ArrayList<IngredientDto> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetectResultBottomSheetListener) {
            onClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root.let {
            if (it.parent != null) {
                (it.parent as ViewGroup).removeView(it)
            }
            it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
        initObservers()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return bottomSheetDialog
    }

    private fun initViews(parentView: View) {
        initBaseView(parentView)

    }

    private fun initBaseView(parentView: View) {
        val bottomSheetBehavior = BottomSheetBehavior.from(parentView.parent as View)
        bottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        dialog?.apply {
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
                (binding.root.parent as View).apply {
//                    minimumHeight = Resources.getSystem().displayMetrics.heightPixels
                    setBackgroundColor(Color.TRANSPARENT)
                }
            }
            window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                )
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            btnFindAll.onClick {
                onClickListener.onResultItemClick(ingredientResults.map { it.idIngredient })
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    private fun initObservers() {
        viewModel.apply {
            observe(liveIngredientFound()) { foundIngredients ->
                foundIngredients?.let {
                    bindFoundIngredientDataView(foundIngredients)
                }
            }
        }
    }

    private fun bindFoundIngredientDataView(ingredients: List<IngredientDto>) {
        ingredientResults.clear()
        ingredientResults.addAll(ingredients)
        binding.apply {
            rvDetectResult.apply {
                detectionResultAdapter = DetectionResultAdapter(ingredients) { ingredient ->
                    requireContext().toast(ingredient.name)
                    if (::onClickListener.isInitialized) {
                        onClickListener.onResultItemClick(listOf(ingredient.idIngredient))
                    }
                }
                adapter = detectionResultAdapter
                layoutManager = GridLayoutManager(
                    requireContext(),
                    3
                )
            }
        }
    }

    companion object {
        const val TAG = "DETECT_RESULT_BOTTOM_SHEET"
        fun newInstance(): DetectionResultBottomSheet {
            return DetectionResultBottomSheet()
        }
    }
}

interface DetectResultBottomSheetListener {
    fun onResultItemClick(ingredient: List<String>)
}
