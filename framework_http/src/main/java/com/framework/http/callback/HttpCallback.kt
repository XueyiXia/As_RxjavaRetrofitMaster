package com.framework.http.callback

import com.framework.http.utils.TypeUtils
import java.lang.reflect.Type

abstract class HttpCallback<T> {

    var type: Type

    constructor() {
        type = TypeUtils.getType(javaClass)
    }

    constructor(type: Type) {
        this.type = type
    }

    /**
     * http请求开始时回调
     */
    open fun onStart(){}

    /**
     * http请求成功时回调
     */
    abstract fun onNext(response: T?)

    /**
     * http请求失败时回调
     */
    abstract fun onError(e: Throwable?)

    /**
     * http请求完成时回调
     */
    open fun onComplete(){}

    /**
     * 上传下载进度回调
     * @param readBytes
     * @param totalBytes
     */
    open fun onProgress(readBytes: Long, totalBytes: Long){}
}