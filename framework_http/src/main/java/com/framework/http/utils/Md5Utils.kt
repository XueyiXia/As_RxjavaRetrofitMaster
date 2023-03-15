package com.framework.http.utils

import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString
import okio.buffer
import okio.source
import java.io.File
import java.security.MessageDigest
/**
 * @author: xiaxueyi
 * @date: 2023-02-22
 * @time: 14:02
 * @说明:
 */
object Md5Utils {

    fun getMD5(str: String) = str.encodeUtf8().md5().hex()

    fun getMD5(file: File?): String? {
        var hash: String? = null
        if (file == null || !file.exists()) {
            return hash
        }
        val messageDigest = MessageDigest.getInstance("MD5")
        file.source().buffer().use {
            val buffer = ByteArray(1024)
            var len = -1
            while (it.read(buffer, 0, buffer.size).also { len = it } != -1) {
                messageDigest.update(buffer, 0, len)
            }
        }
        hash = messageDigest.digest().toByteString().hex()
        return hash
    }


}