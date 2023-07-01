package com.nguyenhl.bk.foodrecipe.feature.presentation.detection

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.longToast
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityDetectionBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.presentation.detection.result.DetectResultBottomSheetListener
import com.nguyenhl.bk.foodrecipe.feature.presentation.detection.result.DetectionResultBottomSheet
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.filter.SearchFilterBottomSheet
import com.otaliastudios.cameraview.CameraException
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Facing
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class DetectionActivity : BaseActivity<ActivityDetectionBinding, DetectionViewModel>(),
    DetectResultBottomSheetListener {
    private lateinit var detectResultBottomSheet: DetectionResultBottomSheet

    private var detectImageRunnable: Runnable? = null
    private val detectImageHandler: Handler = Handler(Looper.getMainLooper())

    private var selectedIngredientResult: ArrayList<String> = arrayListOf()

    private val getGalleryImageResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { getResultFrom(it) }

    private var isBackWithNoDetectAction: Boolean = true

    override fun getLazyBinding() = lazy { ActivityDetectionBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<DetectionViewModel> {
        parametersOf(
            BaseInput.DetectionInput(
                application
            )
        )
    }

    override fun initViews() {
        initCameraView()
        detectResultBottomSheet = DetectionResultBottomSheet.newInstance()
        binding.apply {

        }
    }

    override fun initListener() {
        initCameraListener()
        binding.apply {
            btnBack.onClick {
                isBackWithNoDetectAction = true
                onBackPressed()
            }
            btnTakePicture.onClick {
                detectImageRunnable?.let {
                    detectImageHandler.removeCallbacks(it)
                }
                detectImageRunnable = Runnable {
                    viewModel.setLoading(true)
                    binding.camera.takePicture()
                }
                detectImageRunnable?.let {
                    detectImageHandler.postDelayed(it, 1000)
                }
            }
            btnChangeCamera.onClick {
                toggleCameraFacing()
            }
            btnGallery.onClick {
                openGallery()
            }
            btnRetry.onClick {
                ivPreview.setVisible(false)
                btnRetry.setVisible(false)
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveDetectResult()) { detectResults ->
                detectResults?.let {
                    detectResults.results.ifEmpty {
                        toast("No ingredient found")
                        return@let
                    }
                    val names = detectResults.results.map { it.classResultName }.distinct()
//                    setFoundIngredient(names)
                    val ingredientIds = names.flatMap { addIngredientPrefixToItems(it) }
                    fetchIngredients(ingredientIds)
                    showDetectResultBottomSheet()

                    val nameAsString = names.distinct().joinToString(", ")
                    Timber.tag("PICTURE_1").d(nameAsString)
                }
            }

            observe(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun initCameraView() {
        binding.camera.apply {
            setLifecycleOwner(this@DetectionActivity)
        }
    }

    private fun initCameraListener() {
        binding.camera.apply {
            addCameraListener(object : CameraListener() {
                override fun onCameraOpened(options: CameraOptions) {
                    super.onCameraOpened(options)
                    Timber.d("Camera Opened")
                }

                override fun onCameraClosed() {
                    super.onCameraClosed()
                    Timber.d("Camera Closed")
                }

                override fun onCameraError(exception: CameraException) {
                    super.onCameraError(exception)
                    Timber.e(exception)
                }

                override fun onPictureTaken(result: PictureResult) {
                    super.onPictureTaken(result)
                    viewModel.setLoading(true)
                    viewModel.detectFromImage(result.data, true)
                    bindResultImageView(result.data)
                    Timber.tag("PICTURE_1").d(result.size.toString())
                }
            })
        }
    }

    private fun bindResultImageView(image: ByteArray) {
        binding.apply {
            ivPreview.loadImage(image)

            ivPreview.setVisible(true)
            btnRetry.setVisible(true)
        }
    }

    private fun toggleCameraFacing() {
        binding.camera.apply {
            facing = if (facing == Facing.BACK) {
                Facing.FRONT
            } else {
                Facing.BACK
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        getGalleryImageResultLauncher.launch(intent)
    }

    private fun getResultFrom(result: ActivityResult) {
        if (result.resultCode != RESULT_OK) {
            longToast("No picture selected")
            return
        }
        try {
            val imageUri = result.data?.data
            imageUri?.let {
                val imageStream = contentResolver.openInputStream(it)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                imageStream?.close()

                val byteArrayOutputStream = ByteArrayOutputStream()
                selectedImage?.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
                val imageByteArray = byteArrayOutputStream.toByteArray()
                viewModel.setLoading(true)
                viewModel.detectFromImage(imageByteArray)
                bindResultImageView(imageByteArray)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            longToast("Something went wrong")
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(true)
            progressBar.setVisible(isShow)
        }
    }

    private fun showDetectResultBottomSheet() {
        if (!::detectResultBottomSheet.isInitialized) return
        if (detectResultBottomSheet.isAdded) return
        detectResultBottomSheet.show(supportFragmentManager, SearchFilterBottomSheet.TAG)
    }

    private fun addIngredientPrefixToItems(name: String): List<String> {
        return name.split("_", " ").map {
            "ingredient_${it.removeSuffix("s")}"
        }
    }

    private fun goToSearch(ingredient: String) {
        SearchActivity.startActivity(this) {
            putExtra(KEY_INGREDIENT_RESULT, ingredient)
        }
    }

    override fun onResultItemClick(ingredient: List<String>) {
        selectedIngredientResult.clear()
        selectedIngredientResult.addAll(ingredient)
        onBackPressed()
    }

    override fun onBackPressed() {
        if (!isBackWithNoDetectAction) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putStringArrayListExtra(KEY_INGREDIENT_SEARCH_RESULT, selectedIngredientResult)
            })
        }
        super.onBackPressed()
    }

    companion object {
        const val KEY_INGREDIENT_RESULT = "key_ingredient_result"
        const val KEY_INGREDIENT_SEARCH_RESULT = "key_ingredient_result"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<DetectionActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
