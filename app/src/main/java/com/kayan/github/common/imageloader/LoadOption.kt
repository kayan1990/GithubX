package com.kayan.github.common.imageloader

import android.graphics.Point

/**
 * 图片加载配置
 */
class LoadOption {

    //默认图片
    var mDefaultImg: Int = 0

    //错误图片
    var mErrorImg: Int = 0

    //是否圆形
    var isCircle: Boolean = false

    //是否播放gif
    var isPlayGif: Boolean = true

    //大小
    var mSize: Point? = null

    //图片
    var mUri: Any? = null

    val mTransformations: ArrayList<Any> = ArrayList()

    //这个是比较高级的写法
    fun setDefaultImg(defaultImg: Int) = apply {
        this.mDefaultImg = defaultImg
    }

    fun setErrorImg(errorImg: Int) = apply {
        this.mErrorImg = errorImg
    }

    /**
     * 是否圆形，目前支持fresco 、 glide
     */
    fun setCircle(circle: Boolean) = apply {
        isCircle = circle
    }

    /**
     * 是否播放gif，只支持Fresco目前
     */
    fun setPlayGif(playGif: Boolean) = apply {
        isPlayGif = playGif
    }

    /**
     * 目标尺寸
     */
    fun setSize(size: Point?) = apply {
        this.mSize = size
    }

    /**
     * 播放目标 string、uri、int
     */
    fun setUri(uri: Any) = apply {
        this.mUri = uri
    }

    /**
     * 图片处理
     * picasso https://github.com/wasabeef/picasso-transformations
     * glide   https://github.com/wasabeef/glide-transformations
     * fresco  https://github.com/wasabeef/fresco-processors
     */
    fun setTransformations(transform: Any)= apply {
        mTransformations.add(transform)
    }
}
