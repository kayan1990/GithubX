package com.kayan.github.common.imageloader.giideloader

import android.graphics.drawable.Drawable

import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.kayan.github.common.imageloader.ImageLoader

import java.io.File

/**
 * Glide 图片下载对象
 * Created by kayan on 2022-06-18.
 */
class ImageDownLoadTarget constructor(private val mCallback: ImageLoader.Callback?) : SimpleTarget<File>() {

    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
        mCallback?.onSuccess(resource)
    }

    override fun onLoadStarted(placeholder: Drawable?) {
        super.onLoadStarted(placeholder)
        mCallback?.onStart()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        mCallback?.onFail(null)
    }

}