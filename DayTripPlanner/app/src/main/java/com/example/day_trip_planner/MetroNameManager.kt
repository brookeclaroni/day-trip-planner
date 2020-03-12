package com.example.day_trip_planner

import com.google.android.gms.maps.model.LatLng
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MetroNameManager {
    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        // Set up our OkHttpClient instance to log all network traffic to Logcat
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        builder.connectTimeout(15, TimeUnit.SECONDS)
        builder.readTimeout(15, TimeUnit.SECONDS)
        builder.writeTimeout(15, TimeUnit.SECONDS)

        okHttpClient = builder.build()

    }

    fun retrieveName(
        MetroStationCode : String?,
        apiKey: String
    ): String? {
        var nameString : String? = ""
        if (MetroStationCode != null) {
            val request = Request.Builder()
                .url("https://api.wmata.com/Rail.svc/json/jStationInfo?StationCode=$MetroStationCode")
                .header(
                    "api_key",
                    apiKey
                )
                .build()

            val response = okHttpClient.newCall(request).execute()

            val responseString: String? = response.body?.string()
            if (!responseString.isNullOrEmpty() && response.isSuccessful) {
                val json = JSONObject(responseString)
                nameString = json.getString("Name")
            }
        }
        return nameString
    }

}
