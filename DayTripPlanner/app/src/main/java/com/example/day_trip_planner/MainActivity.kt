package com.example.day_trip_planner

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var destination : EditText
    private lateinit var foodSpin : Spinner
    private lateinit var foodSeek : SeekBar
    private lateinit var foodNumText : TextView
    private lateinit var attractionSpin : Spinner
    private lateinit var attractionSeek : SeekBar
    private lateinit var attractionNumText : TextView
    private lateinit var go : Button
    private var destinationSet : Boolean = false
    private var foodSet : Boolean = false
    private var attractionSet : Boolean = false
    private var foodIndex : Int = 0
    private var attractionIndex : Int = 0
    private var foodNum : Int = 2
    private var attractionNum : Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences = getSharedPreferences(
            "day-trip-planner",
            Context.MODE_PRIVATE
        )

        // Tells Android which layout file should be used for this screen.
        setContentView(R.layout.activity_main)

        // The IDs match what was set in the "id" field in our XML layout
        destination = findViewById(R.id.destination_text_box)
        go = findViewById(R.id.go_button)
        foodSpin = findViewById(R.id.food_spinner)
        foodSeek = findViewById(R.id.food_seek_bar)
        foodNumText = findViewById(R.id.food_num_results_text)
        attractionSpin = findViewById(R.id.attractions_spinner)
        attractionSeek = findViewById(R.id.attractions_seek_bar)
        attractionNumText = findViewById(R.id.attractions_num_results_text)

        // Using a lambda to implement a View.OnClickListener interface.
        go.setOnClickListener {
            // Save destination credentials to file
            val inputtedDestination: String = destination.text.toString()

            preferences
                .edit()
                .putString("destination", inputtedDestination)
                .apply()

            val choices = listOf(destination.text.toString())
            val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice)
            arrayAdapter.addAll(choices)

            //display dialog box w radio button and toast  when go is clicked
            AlertDialog.Builder(this)
                .setTitle("Search Results")
                .setAdapter(arrayAdapter) { _ , which ->
                    Toast.makeText(this, "You picked: ${choices[which]}", Toast.LENGTH_SHORT).show()
                }

                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        //populate the food spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.cuisine_choices,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            foodSpin.adapter = adapter
        }

        //don't allow go to work until food is selected
        foodSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                foodSet = false;
                go.isEnabled = false
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                foodIndex = position

                foodSet = false;
                if (position>0)
                    foodSet = true

                go.isEnabled = false
                if (destinationSet && foodSet && attractionSet)
                    go.isEnabled = true

                val inputtedFoodIndex: Int = foodSpin.getSelectedItemPosition()

                preferences
                    .edit()
                    .putInt("foodIndex", inputtedFoodIndex)
                    .apply()
            }
        }

        //don't allow go to work until attraction is selected
        attractionSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                attractionSet = false;
                go.isEnabled = false
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                attractionSet = false
                if (position>0)
                    attractionSet = true

                go.isEnabled = false
                if (destinationSet && foodSet && attractionSet)
                    go.isEnabled = true

                val inputtedAttractionIndex: Int = attractionSpin.getSelectedItemPosition()

                preferences
                    .edit()
                    .putInt("attractionIndex", inputtedAttractionIndex)
                    .apply()
            }
        }


        //populate the attraction spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.attraction_choices,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            attractionSpin.adapter = adapter
        }

        //dynamically update the food seek bar value
        foodSeek.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {

                preferences
                    .edit()
                    .putInt("foodNum", progress)
                    .apply()

                foodNumText.setText(getString(R.string.num_food_results, progress))
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        //dynamically update the attraction seek bar value
        attractionSeek.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                preferences
                    .edit()
                    .putInt("attractionNum", progress)
                    .apply()

                attractionNumText.setText(getString(R.string.num_attraction_results, progress))
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        go.isEnabled = false

        destination.addTextChangedListener(textWatcher)

        val savedDestination = preferences.getString("destination", "")
        val savedFoodIndex = preferences.getInt("foodIndex", 0)
        val savedAttractionIndex = preferences.getInt("attractionIndex", 0)
        val savedFoodNum = preferences.getInt("foodNum", 2)
        val savedAttractionNum = preferences.getInt("attractionNum", 2)

        //restore old data
        destination.setText(savedDestination)
        foodIndex = savedFoodIndex
        foodSpin.setSelection(foodIndex)
        attractionIndex = savedAttractionIndex
        attractionSpin.setSelection(attractionIndex)
        foodNum = savedFoodNum
        foodSeek.setProgress(foodNum)
        attractionNum = savedAttractionNum
        attractionSeek.setProgress(attractionNum)
        foodNumText.setText(getString(R.string.num_food_results, savedFoodNum))
        attractionNumText.setText(getString(R.string.num_attraction_results, savedAttractionNum))

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val inputtedDestination : String = destination.text.toString()

            destinationSet = false
            if(inputtedDestination.trim().isNotEmpty())
                destinationSet = true

            go.isEnabled = false
            if (destinationSet && foodSet && attractionSet)
                go.isEnabled = true
        }
    }
}
