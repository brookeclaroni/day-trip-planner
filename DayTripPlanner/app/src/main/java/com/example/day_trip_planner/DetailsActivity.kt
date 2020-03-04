package com.example.day_trip_planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val entries = getFakeEntries()
        val adapter = EntryAdapter(entries)
        recyclerView.adapter = adapter

        if (entries.isEmpty())
        {
            Toast.makeText(this, "There are no results.", Toast.LENGTH_LONG).show()
        }
    }

    fun getFakeEntries(): List<Entry> {
        return listOf(
            Entry(
                name = "Ford’s Theatre",
                pricePoint = null,
                rating = "4.5",
                address = "511 10th St NW, Washington, DC 20004",
                phone = "+12023474833",
                url = "https://www.yelp.com/biz/fords-theatre-washington?adjust_creative=keu-kOIeln4R7XPAEsPSYg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=keu-kOIeln4R7XPAEsPSYg"
            ),
            Entry(
                name = "Ford’s Theatre 2",
                pricePoint = "$$",
                rating = "4.0",
                address = "511 10th St NW, Washington, DC 20004",
                phone = "+12023474833",
                url = "https://www.yelp.com/biz/fords-theatre-washington?adjust_creative=keu-kOIeln4R7XPAEsPSYg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=keu-kOIeln4R7XPAEsPSYg"
            ),
            Entry(
                name = "Ford’s Theatre 3",
                pricePoint = "$$$",
                rating = "2.5",
                address = "511 10th St NW, Washington, DC 20004",
                phone = null,
                url = "https://www.yelp.com/biz/fords-theatre-washington?adjust_creative=keu-kOIeln4R7XPAEsPSYg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=keu-kOIeln4R7XPAEsPSYg"
            ),
            Entry(
                name = "Ford’s Theatre 4",
                pricePoint = null,
                rating = "1.25",
                address = "511 10th St NW, Washington, DC 20004",
                phone = null,
                url = "https://www.yelp.com/biz/fords-theatre-washington?adjust_creative=keu-kOIeln4R7XPAEsPSYg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=keu-kOIeln4R7XPAEsPSYg"
            ),
            Entry(
                name = "Wicked Waffle",
                pricePoint = "$",
                rating = "4",
                address = "1712 I St NW, Washington, DC 20006",
                phone = "+12029442700",
                url = "https://www.yelp.com/biz/wicked-waffle-washington"
            )
        )
    }
}
