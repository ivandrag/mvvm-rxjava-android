package ro.dragosivanov.domain.api

import okhttp3.Interceptor
import okhttp3.Response

class GoRestApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header(
                "Authorization",
                "Bearer 4ff082b8e37b9816230c6e205d88822906939c6f2907dfba618e1bbab0b32e90"
            )
            .build()

        return chain.proceed(request);
    }
}
