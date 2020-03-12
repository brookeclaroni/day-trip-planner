package com.example.day_trip_planner

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class YelpManager {
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

    fun retrieveEntries(
        latitude: Double?,
        longitude: Double?,
        category: String?,
        limit: Int?,
        apiKey: String
    ): MutableList<Entry> {

        val request = Request.Builder()
            .url("https://api.yelp.com/v3/businesses/search?latitude=$latitude&longitude=$longitude&categories=$category&limit=$limit")
            .header(
                "Authorization",
                apiKey
            )
            .build()

        val response = okHttpClient.newCall(request).execute()

        val entries: MutableList<Entry> = mutableListOf()
        val responseString: String? = response.body?.string()

        if (!responseString.isNullOrEmpty() && response.isSuccessful) {
            val json: JSONObject = JSONObject(responseString)
            val results: JSONArray = json.getJSONArray("businesses")

            for (i in 0 until results.length()) {
                val curr = results.getJSONObject(i)

                val currName = curr.getString("name")
                var currPricePoint = ""
                if(curr.has("price"))
                   currPricePoint = curr.getString("price")
                val currRating = curr.getInt("rating")
                val loc = curr.getJSONObject("location")
                val currAddress = loc.getString("address1")
                val currAddress2 = "${loc.getString("city")} ${loc.getString("zip_code")}"
                var currPhone : String? = null
                if(curr.has("phone"))
                    currPhone = curr.getString("phone")
                val currUrl = curr.getString("url")
                val currCoordinates = curr.getJSONObject("coordinates")
                val currLat = currCoordinates.getDouble("latitude")
                val currLon = currCoordinates.getDouble("longitude")

                val entry = Entry(
                    name = currName,
                    pricePoint = currPricePoint,
                    rating = currRating.toString(),
                    address = currAddress,
                    address2 = currAddress2,
                    phone = currPhone,
                    url = currUrl,
                    lat = currLat,
                    lon = currLon
                )

                entries.add(entry)
            }
        }

        return entries
    }
}
