package com.example.day_trip_planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

class DetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val lat = intent.getDoubleExtra("LATITUDE", 0.0)
        val lon = intent.getDoubleExtra("LONGITUDE", 0.0)
        val foodType = intent.getStringExtra("FOOD_TYPE")
        val foodLimit = intent.getIntExtra("FOOD_NUM", 0)
        val attrType = intent.getStringExtra("ATTR_TYPE")
        val attrLimit = intent.getIntExtra("ATTR_NUM", 0)

        doAsync {
            val yelpManager = YelpManager()
            try {
                val entries = yelpManager.retrieveEntries(lat, lon, foodType.toLowerCase(), foodLimit, getString(R.string.yelp_key))
                val entries2 =  yelpManager.retrieveEntries(lat, lon, attrType.toLowerCase(), attrLimit, getString(R.string.yelp_key))
                entries.addAll(entries2)
                entries.sortByDescending{it.rating}

                runOnUiThread {
                    val adapter = EntryAdapter(entries)
                    recyclerView.adapter = adapter
                }
                if (entries.isEmpty())
                {
                    runOnUiThread {
                        Toast.makeText(this@DetailsActivity, "There are no results.", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@DetailsActivity,
                        "Failed to retrieve details",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        //Toast.makeText(this, "You asked for ${intent.getIntExtra("FOOD_NUM", 0)} ${intent.getStringExtra("FOOD_TYPE")}\n and ${intent.getIntExtra("ATTR_NUM", 0)} ${intent.getStringExtra("ATTR_TYPE")}", Toast.LENGTH_SHORT).show()


    }
}
