package com.ddd.airplane.common.views.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ddd.airplane.R
import com.ddd.airplane.databinding.ImageLoadViewBinding
import kotlinx.android.synthetic.main.image_load_view.view.*
import timber.log.Timber

/**
 *
 * ImageLoaderView
 *
 * @author jess
 */
class ImageLoadView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding = ImageLoadViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var corners: Int = 0
    private var isCircle: Boolean = false

    /**
     * 라운드 설정
     *
     * @param corners
     */
    fun setCorners(corners: Int) = apply {
        this.corners = corners
    }

    /**
     * 원 여부
     *
     * @param isCircle
     */
    fun setCircle(isCircle: Boolean) = apply {
        this.isCircle = isCircle
    }


    @SuppressLint("CheckResult")
    fun load(url: String) {
        Timber.d(">> load $url")

        val glide = Glide.with(iv_succeed)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()

        if (corners > 0) {
            glide.apply(
                RequestOptions.bitmapTransform(
                    RoundedCorners(
                        context.resources.getDimensionPixelSize(
                            R.dimen.dp8
                        )
                    )
                )
            )
        }

        glide.listener(object : RequestListener<Drawable> {

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                binding.isLoaded = true
                Timber.d(">> onResourceReady $url")
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                binding.isLoaded = false
                Timber.d(">> onLoadFailed $url")
                Timber.d(">> onLoadFailed ${e?.message}")
                return false
            }
        }).into(iv_succeed)
    }
}