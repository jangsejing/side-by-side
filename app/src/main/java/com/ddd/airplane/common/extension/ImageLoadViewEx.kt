package com.ddd.airplane.common.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ddd.airplane.common.utils.tryCatch
import com.ddd.airplane.common.views.component.ImageLoadView
import timber.log.Timber

/**
 * 이미지 로드
 */
@BindingAdapter(value = ["url", "corners", "circle"], requireAll = false)
fun ImageView.loadImage(
    url: String?,
    corners: Float = 0.0f,
    circle: Boolean = false
) {
    tryCatch {
        Timber.d(">> $url")
        url?.let {
            this.post {
                val glide = Glide.with(this)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()

                if (corners > 0) {
                    glide.apply(
                        RequestOptions.bitmapTransform(
                            RoundedCorners(
                                corners.toInt()
                            )
                        )
                    )
                }
                glide.into(this)
            }
        }
    }
}

/**
 * 이미지 로드
 */
@BindingAdapter(value = ["url", "corners", "circle"], requireAll = false)
fun ImageLoadView.loadImage(
    url: String?,
    corners: Int = 0,
    circle: Boolean = false
) {
    tryCatch {
        url?.let {
            val view = this
            view.post {
                view.setCorners(corners)
                    .setCircle(circle)
                    .load(url)
            }

        }
    }
}