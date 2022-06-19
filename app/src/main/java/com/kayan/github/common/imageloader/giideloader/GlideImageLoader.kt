package com.kayan.github.common.imageloader.giideloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.disklrucache.DiskLruCache
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.MemoryCache
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.EmptySignature
import com.kayan.github.common.imageloader.ImageConst
import com.kayan.github.common.imageloader.ImageLoader
import com.kayan.github.common.imageloader.LoadOption
import com.kayan.github.common.utils.ReflectionHelpers
import java.io.File


/**
 * Glide 图片加载
 * Created by kayan on 2022-06-18.
 */
class GlideImageLoader(private val context: Context) : ImageLoader {


    override fun loadImage(loadOption: LoadOption, target: Any?, callback: ImageLoader.Callback?, extendOption: ImageLoader.ExtendedOptions?) {
        if (target !is ImageView) {
            throw IllegalStateException("target must be ImageView")
        }
        loadImage(loadOption, extendOption)
                .load(loadOption.mUri)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        callback?.let {
                            it.onFail(e)
                        }
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        callback?.let {
                            it.onSuccess(resource)
                        }
                        return false
                    }
                })
                .into(target)
    }

    override fun clearCache(type: Int) {
        when (type) {
            ImageConst.CLEAR_ALL_CACHE -> {
                Glide.get(context.applicationContext).clearMemory()
                Glide.get(context.applicationContext).clearDiskCache()
            }
            ImageConst.CLEAR_MEMORY_CACHE ->
                Glide.get(context.applicationContext).clearMemory()
            ImageConst.CLEAR_DISK_CACHE ->
                Glide.get(context.applicationContext).clearDiskCache()
        }
    }

    override fun clearCacheKey(type: Int, loadOption: LoadOption) {
        val deleteDisk = {
            val diskCache = DiskLruCacheWrapper.create(Glide.getPhotoCacheDir(context), (250 * 1024 * 1024).toLong())
            val key = GlideCacheKey(loadOption.mUri as String, EmptySignature.obtain())
            diskCache.delete(key)
        }
        val deleteMemory = {
            try {
                val key = GlideCacheKey(loadOption.mUri as String, EmptySignature.obtain());
                val cache = ReflectionHelpers.getField<MemoryCache>(Glide.get(context.applicationContext), "memoryCache")
                cache.remove(key)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        when (type) {
            ImageConst.CLEAR_ALL_CACHE -> {
                deleteMemory.invoke()
                deleteDisk.invoke()
            }
            ImageConst.CLEAR_MEMORY_CACHE -> {
                deleteMemory()
            }
            ImageConst.CLEAR_DISK_CACHE -> {
                deleteDisk.invoke()
            }

        }
    }

    override fun getLocalCache(loadOption: LoadOption, extendOption: ImageLoader.ExtendedOptions?): File? {
        val future = loadImage(loadOption, extendOption)
                .asFile().load(loadOption.mUri)
        return future.submit().get()
    }


    override fun isCache(loadOption: LoadOption, extendOption: ImageLoader.ExtendedOptions?): Boolean {
        // 寻找缓存图片
        val file = DiskLruCacheWrapper.create(Glide.getPhotoCacheDir(context), (250 * 1024 * 1024).toLong())
                .get(GlideCacheKey(loadOption.mUri as String, EmptySignature.obtain()))
        return file != null
    }

    override fun getLocalCacheBitmap(loadOption: LoadOption, extendOption: ImageLoader.ExtendedOptions?): Bitmap? {
        val future = loadImage(loadOption, extendOption)
                .asBitmap().load(loadOption.mUri)
        return future.submit().get()
    }


    override fun getCacheSize(): Long? {
        val cache =  DiskLruCacheWrapper.create(Glide.getPhotoCacheDir(context), (250 * 1024 * 1024).toLong())
        val diskLruCache = ReflectionHelpers.getField<DiskLruCache>(cache, "diskLruCache")
        return diskLruCache.size()
    }

    override fun downloadOnly(loadOption: LoadOption, callback: ImageLoader.Callback?, extendOption: ImageLoader.ExtendedOptions?) {
        loadImage(loadOption, extendOption).downloadOnly().load(loadOption.mUri).into(ImageDownLoadTarget(callback))
    }

    private fun loadImage(loadOption: LoadOption, extendOption: ImageLoader.ExtendedOptions?): RequestManager {
        return Glide.with(context.applicationContext)
                .setDefaultRequestOptions(getOption(loadOption, extendOption))
    }

    @SuppressWarnings("CheckResult")
    private fun getOption(loadOption: LoadOption, extendOption: ImageLoader.ExtendedOptions?): RequestOptions {
        val requestOptions = RequestOptions()
        if (loadOption.mErrorImg > 0) {
            requestOptions.error(loadOption.mErrorImg)
        }
        if (loadOption.mDefaultImg > 0) {
            requestOptions.placeholder(loadOption.mDefaultImg)
        }
        if (loadOption.isCircle) {
            requestOptions.circleCrop()
        }
        loadOption.mSize?.let {
            requestOptions.override(it.x, it.y)
        }
        if(loadOption.mTransformations.isNotEmpty()) {
            requestOptions.transform(MultiTransformation(loadOption.mTransformations as ArrayList<Transformation<Bitmap>>))
        }
        extendOption?.let {
            extendOption.onOptionsInit(requestOptions)
        }
        return requestOptions
    }

}

