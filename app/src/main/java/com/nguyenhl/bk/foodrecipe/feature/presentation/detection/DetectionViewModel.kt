package com.nguyenhl.bk.foodrecipe.feature.presentation.detection

import android.os.SystemClock
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.threadrelated.runOnMainThread
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.detection.toDetectResultDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.detection.DetectionResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.detection.toDetectImageResultDto
import com.nguyenhl.bk.foodrecipe.feature.data.repository.DetectionRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.detect.DetectImageResultDto
import com.nguyenhl.bk.foodrecipe.feature.util.ImageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.nio.charset.StandardCharsets

class DetectionViewModel(
    val input: BaseInput.DetectionInput,
    private val detectionRepository: DetectionRepository
) : BaseViewModel(input) {

    private val _detectResults: MutableLiveData<DetectImageResultDto?> = MutableLiveData()
    fun liveDetectResult(): LiveData<DetectImageResultDto?> = _detectResults

    private val _foundIngredients: MutableLiveData<List<String>?> = MutableLiveData()
    fun liveFoundIngredient(): LiveData<List<String>?> = _foundIngredients
    fun setFoundIngredient(ingredients: List<String>) { _foundIngredients.value = ingredients }

    fun detectFromImage(imageBytes: ByteArray, isFromCamera: Boolean = false) {
        val scaleFactor = if (isFromCamera) {
            0.6f
        } else {
            1f
        }

        val resizeImageBytes = ImageUtils.scaleDownImage(imageBytes, scaleFactor)
        val encodedFile =
            String(Base64.encode(resizeImageBytes, Base64.DEFAULT), StandardCharsets.US_ASCII)

        viewModelScope.launch(Dispatchers.IO) {
            detectionRepository.detectObject("test_${SystemClock.uptimeMillis()}.jpg", encodedFile)
                .collectLatest { response ->
                    setLoading(false)
                    when (response) {
                        is DetectionResponse -> {
                            _detectResults.postValue(response.toDetectImageResultDto())
                        }

                        is ErrorResponse -> {
                            Timber.tag("PICTURE_1").w(response.message)
                            withContext(Dispatchers.Main) {
                                input.application.toast(response.message)
                            }
                        }
                        else -> {
                            Timber.tag("PICTURE_1").w("Exception happened")
                            withContext(Dispatchers.Main) {
                                input.application.toast("Exception happened")
                            }
                            _detectResults.postValue(null)
                        }
                    }
                }
        }
    }
}
