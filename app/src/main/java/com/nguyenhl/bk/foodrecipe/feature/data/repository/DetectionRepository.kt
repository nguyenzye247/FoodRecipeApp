package com.nguyenhl.bk.foodrecipe.feature.data.repository

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.DetectionErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.DetectionService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DetectionRepository : Repository {
    private val detectionService = DetectionService

    fun detectObject(imageName: String, encodedImage: String) = flow {
        detectionService.apiDetect.uploadImage(encodedImage, imageName)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(DetectionErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)
}
