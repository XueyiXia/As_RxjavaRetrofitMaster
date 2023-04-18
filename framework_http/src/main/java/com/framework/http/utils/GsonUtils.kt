package com.framework.http.utils

import android.text.TextUtils
import com.google.gson.Gson
import java.lang.reflect.Type

object GsonUtils {

    private val gson = Gson()

    fun toJson(obj: Any?): String {
        var gsonString = ""
        if (obj != null && !TextUtils.isEmpty(obj.toString())) {
            gsonString = gson.toJson(obj)
        }
        return gsonString
    }

    fun <T> fromJson(str:String,type: Type):T=gson.fromJson(str,type)
}