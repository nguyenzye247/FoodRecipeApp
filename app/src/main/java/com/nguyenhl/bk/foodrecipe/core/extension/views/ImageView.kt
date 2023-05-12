/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.nguyenhl.bk.foodrecipe.core.extension.views

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes

/**
 * Sets a drawable resource as the content of this ImageView.
 *
 * **This does Bitmap reading and decoding on the UI thread, which can cause a latency hiccup.**
 * If that's a concern, consider using [imageDrawable] or [imageBitmap] and [BitmapFactory] instead.
 */
inline var ImageView.imageResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@DrawableRes value) = setImageResource(value)

/**
 * Sets a drawable as the content of this ImageView.
 * A null value will clear the content.
 */
inline var ImageView.imageDrawable: Drawable?
    get() = drawable
    set(value) = setImageDrawable(value)

/**
 * Sets a Bitmap as the content of this ImageView.
 * @see BitmapFactory
 */
inline var ImageView.imageBitmap: Bitmap
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) = setImageBitmap(value)
