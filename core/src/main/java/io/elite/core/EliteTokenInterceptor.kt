package io.elite.core

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by loki on 18/08/16.
 */

class EliteTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response
        val builder = request.newBuilder()
        builder.addHeader("App-Name", Elite.instance.appName)
        if (Elite.instance.requestType) {
            if (Elite.instance.userToken != "") {
                builder.addHeader(
                    "Authorization", Elite.instance.authIdentifier + Elite.instance.userToken!!
                )
            }
            if (Elite.instance.userRole != "") {
                builder.addHeader(
                    "x-hasura-role", Elite.instance.userRole
                )
            }
            if (Elite.instance.sessionId != "") {
                builder.addHeader(
                    "x-session-id", Elite.instance.sessionId!!
                )
            }
            if (Elite.instance.version != "") {
                builder.addHeader(
                    "version", Elite.instance.version!!
                )
            }
            builder.addHeader(
                "platform", "Android"
            )
        }

        val newRequest = builder.build()
        response = chain.proceed(newRequest)
        return response
    }
}
