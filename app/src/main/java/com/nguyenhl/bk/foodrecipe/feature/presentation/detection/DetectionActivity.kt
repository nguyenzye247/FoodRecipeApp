package com.nguyenhl.bk.foodrecipe.feature.presentation.detection

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.longToast
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityDetectionBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.otaliastudios.cameraview.CameraException
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.CameraUtils
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Facing
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class DetectionActivity : BaseActivity<ActivityDetectionBinding, DetectionViewModel>() {
    private var detectImageRunnable: Runnable? = null
    private val detectImageHandler: Handler = Handler(Looper.getMainLooper())

    private val getGalleryImageResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { getResultFrom(it) }

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
        binding.apply {

        }
    }

    override fun initListener() {
        initCameraListener()
        binding.apply {
            btnBack.onClick {
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
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveDetectResult()) { detectResults ->
                detectResults?.let {
                    val names = detectResults.results.map { it.classResultName }
                    val nameAsString = names.distinct().joinToString(", ")
                    toast(nameAsString.ifEmpty { "No ingredient found" })
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
                    viewModel.detectFromImage(result.data)
                    Timber.tag("PICTURE_1").d(result.size.toString())
                }
            })
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

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(true)
            progressBar.setVisible(isShow)
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
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            longToast("Something went wrong")
        }
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<DetectionActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
