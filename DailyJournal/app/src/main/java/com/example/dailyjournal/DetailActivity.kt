package com.example.dailyjournal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO - implement the Activity
        /*
        val INTENT_DATA = "course.labs.locationlab.placerecord.IntentData"
        setContentView(R.layout.place_badge_detail)

        var badgeintent = intent
        var intentData = badgeintent.getParcelableExtra(INTENT_DATA) as Intent
        var record = PlaceRecord(intentData)

        findViewById<TextView>(R.id.country_name).text = record.countryName
        findViewById<TextView>(R.id.place_name).text = record.place
        findViewById<TextView>(R.id.date_visited).text = record.dateVisited.toString()
        findViewById<TextView>(R.id.gps_coordinates).text = record.location?.latitude.toString() + " " + record.location?.longitude.toString()

        if(record.flagBitmap != null){
            findViewById<ImageView>(R.id.flag).setImageBitmap(record.flagBitmap)
        } */
    }
}