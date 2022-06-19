package com.kayan.github.common.imageloader.giideloader

import com.bumptech.glide.load.Key
import java.security.MessageDigest

/**
 * Glide原图缓存Key
 * Created by kayan on 2022-06-18.
 */
class GlideCacheKey constructor(private val id: String, private val signature: Key) : Key {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val that = o as GlideCacheKey?

        return id == that!!.id && signature == that.signature
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + signature.hashCode()
        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(id.toByteArray(charset(Key.STRING_CHARSET_NAME)))
        signature.updateDiskCacheKey(messageDigest)
    }
}