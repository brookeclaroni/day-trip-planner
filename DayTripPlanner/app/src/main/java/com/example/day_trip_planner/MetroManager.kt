package com.example.day_trip_planner

import com.google.android.gms.maps.model.LatLng
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MetroManager {
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

    fun retrieveEntry(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): MetroStation? {
        val radius = "1500"

        val request = Request.Builder()
            .url("https://api.wmata.com/Rail.svc/json/jStationEntrances?Lat=$latitude&Lon=$longitude&Radius=$radius")
            .header(
                "api_key",
                apiKey
            )
            .build()

        val response = okHttpClient.newCall(request).execute()

        val responseString: String? = response.body?.string()
        var entry : MetroStation? = null
        if (!responseString.isNullOrEmpty() && response.isSuccessful) {
            val json = JSONObject(responseString)
            val results: JSONArray = json.getJSONArray("Entrances")

            val curr = results.getJSONObject(0)

            val metroNameManager = MetroNameManager()
            entry = MetroStation(
                name = metroNameManager.retrieveName(curr.getString("StationCode1"), apiKey),
                code = curr.getString("StationCode1"),
                lat = curr.getDouble("Lat"),
                lon = curr.getDouble("Lon")
            )
        }
        return entry
    }
}
