package es.webandroid.marvel.framework.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkServiceProvider {
    companion object {
        private const val CONNECT_TIMEOUT: Long = 15
        private const val READ_TIMEOUT: Long = 15
        private const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        private const val PARAM_TS = "1"
        private const val TS = "ts"
        private const val PARAM_APIKEY = "4468a99121a605340439385131893c6a"
        private const val APIKEY = "apikey"
        private const val PARAM_HASH = "43d484aafeb6e7dc691069175565b3ef"
        private const val HASH = "hash"
    }

    private fun client(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                val url = request
                    .url
                    .newBuilder()
                    .addQueryParameter(
                        TS,
                        PARAM_TS
                    )
                    .addQueryParameter(
                        APIKEY,
                        PARAM_APIKEY
                    )
                    .addQueryParameter(
                        HASH,
                        PARAM_HASH
                    )
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }

    private fun moshi() = Moshi.Builder().build()

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .client(client())
            .build()
    }

    fun networkApi(): NetworkApi = retrofit().create(NetworkApi::class.java)
}
