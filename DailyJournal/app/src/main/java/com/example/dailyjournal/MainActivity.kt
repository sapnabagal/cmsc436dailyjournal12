package com.example.dailyjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

public lateinit var addFab: FloatingActionButton
public lateinit var audioFab: FloatingActionButton
public lateinit var mediaFab: FloatingActionButton
public lateinit var textFab: FloatingActionButton
public var allFabsVisible: Boolean = false


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFab = findViewById<FloatingActionButton>(R.id.fab)
        audioFab = findViewById<FloatingActionButton>(R.id.audio)
        textFab= findViewById<FloatingActionButton>(R.id.text)
        mediaFab= findViewById<FloatingActionButton>(R.id.media)

        audioFab.visibility = View.GONE
        mediaFab.visibility = View.GONE
        textFab.visibility = View.GONE
        addFab.setOnClickListener{
            if(!allFabsVisible){
                audioFab.show()
                mediaFab.show()
                textFab.show()
                allFabsVisible = true
            }else{
                audioFab.hide()
                mediaFab.hide()
                textFab.hide()
                allFabsVisible = false


            }
            Toast.makeText(this@MainActivity, "Fab is clicked", Toast.LENGTH_SHORT).show()
        }


    }
}