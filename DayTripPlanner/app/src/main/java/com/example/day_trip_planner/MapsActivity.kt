package com.example.day_trip_planner

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.widget.Button
import com.google.android.gms.maps.model.*
import org.jetbrains.anko.doAsync



class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val detailsButton: Button = findViewById(R.id.detailsButton)

        val lat =  intent.getDoubleExtra("LATITUDE", 0.0)
        val lon  =  intent.getDoubleExtra("LONGITUDE", 0.0)
        val foodNum = intent.getIntExtra("FOOD_NUM",0)
        val attrNum = intent.getIntExtra("ATTR_NUM",0)
        val foodType = intent.getStringExtra("FOOD_TYPE")
        val attrType = intent.getStringExtra("ATTR_TYPE")

        detailsButton.setOnClickListener{
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("LATITUDE", lat)
            intent.putExtra("LONGITUDE", lon)
            intent.putExtra("FOOD_NUM", foodNum)
            intent.putExtra("ATTR_NUM",  attrNum)
            intent.putExtra("FOOD_TYPE",   foodType)
            intent.putExtra("ATTR_TYPE",  attrType)
            startActivity(intent)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val currentLocationString = intent.getStringExtra("LOCATION")
        val lat: Double = intent.getDoubleExtra("LATITUDE", 0.0)
        val lon: Double = intent.getDoubleExtra("LONGITUDE",0.0)
        var mark = LatLng(lat, lon)
        mMap.addMarker(MarkerOptions().position(mark).title(currentLocationString))


        val foodNum = intent.getIntExtra("FOOD_NUM",0)
        val attrNum = intent.getIntExtra("ATTR_NUM",0)
        val foodType = intent.getStringExtra("FOOD_TYPE")
        val attrType = intent.getStringExtra("ATTR_TYPE")

        doAsync {
            val yelpManager = YelpManager()
            val metroManager = MetroManager()
            val metroNameManager = MetroNameManager()
            try {

                val foodEntries = yelpManager.retrieveEntries(lat, lon, foodType.toLowerCase(), foodNum, getString(R.string.yelp_key))

                if (foodEntries.size>0) {


                    foodEntries.forEach{
                        runOnUiThread {
                            mMap.addMarker(MarkerOptions().position(LatLng(it.lat, it.lon)).title(it.name).icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_GREEN)))
                        }
                    }
                }

                val attrEntries = yelpManager.retrieveEntries(lat, lon, attrType.toLowerCase(), attrNum, getString(R.string.yelp_key))
                if (attrEntries.size>0) {
                    attrEntries.forEach{
                        runOnUiThread {
                            mMap.addMarker(MarkerOptions().position(LatLng(it.lat, it.lon)).title(it.name).icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_BLUE)))
                        }
                    }
                }

                var metroStation = metroManager.retrieveEntry(lat, lon, getString(R.string.metro_key))

                if (metroStation != null) {
                    runOnUiThread {
                            mMap.addMarker(MarkerOptions().position(LatLng(metroStation.lat, metroStation.lon)).title(metroStation.name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)))

                    }
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@MapsActivity,
                        "Failed to retrieve destinations",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        val zoomLevel = 13.5f
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(mark, zoomLevel)
        )
        mMap.addCircle(
            CircleOptions()
                .center(mark)
                .radius(1500.0)
                .strokeColor(Color.BLACK)
        )
    }
}
