package com.example.day_trip_planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                pricePoint = "$",
                rating = "1.25",
                address = "511 10th St NW, Washington, DC 20004",
                phone = null,
                url = "https://www.yelp.com/biz/fords-theatre-washington?adjust_creative=keu-kOIeln4R7XPAEsPSYg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=keu-kOIeln4R7XPAEsPSYg"
            ),
            Entry(
                name = "Ford’s Theatre 5",
                pricePoint = null,
                rating = "0.5",
                address = "511 10th St NW, Washington, DC 20004",
                phone = "+12023474833",
                url = "https://www.yelp.com/biz/fords-theatre-washington?adjust_creative=keu-kOIeln4R7XPAEsPSYg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=keu-kOIeln4R7XPAEsPSYg"
            )
        )
    }
}
