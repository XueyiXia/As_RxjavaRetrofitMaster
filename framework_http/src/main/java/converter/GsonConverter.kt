package converter

import com.framework.http.converter.IConverter
import com.framework.http.utils.GsonUtils
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import java.lang.reflect.Type

class GsonConverter<T> : IConverter<T> {

    @Throws(Exception::class)
    override fun convert(body: ResponseBody, type: Type): T {
        var response: T? = null
        val result = body.string()
        body.close()
        try {
            response = GsonUtils.fromJson(result,type) as T
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            throw e
        }

        return response
    }
}