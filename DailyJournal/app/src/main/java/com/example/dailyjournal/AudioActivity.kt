package com.example.dailyjournal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.record_audio.*
import java.io.File
import java.io.IOException

class AudioActivity : AppCompatActivity(), View.OnClickListener {

    private var mRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null
    private var fileName: String? = null
    private val RECORD_AUDIO_REQUEST_CODE = 101
    private var isPlaying = false
    private lateinit var imgBtRecord : ImageView;
    private lateinit var imgBtStop : ImageView;


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgBtRecord -> {
                prepareRecording()
                startRecording()
            }

            R.id.imgBtStop -> {
                prepareStop()
                stopRecording()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("AudioActivity", "inOncreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.record_audio)

        imgBtRecord = findViewById(R.id.imgBtRecord)
        imgBtStop = findViewById(R.id.imgBtStop)

        imgBtRecord.setOnClickListener(this)
        imgBtStop.setOnClickListener(this)

    }

    private fun prepareStop() {
        imgBtRecord.visibility = View.VISIBLE
        imgBtStop.visibility = View.GONE
    }


    private fun prepareRecording() {
        imgBtRecord.visibility = View.GONE
        imgBtStop.visibility = View.VISIBLE
    }


    private fun startRecording() {
        Log.i("AudioActivity", "Starting Recording");
        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        //root = android.os.Environment.getExternalStorageDirectory()
        val root = applicationContext.getExternalFilesDir(null)
        val file = File(root!!.absolutePath + "/DailyJournal/Audios")
        if (!file.exists()) {
            file.mkdirs()
        }

        fileName = root.absolutePath + "/DailyJournal/Audios/" + (System.currentTimeMillis().toString() + ".mp3")

        Log.d("filename", fileName!!)
        mRecorder!!.setOutputFile(fileName)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mRecorder!!.prepare()
            mRecorder!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // making the imageView a stop button starting the chronometer
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
    }


    private fun stopRecording() {
        Log.i("AudioActivity", "Stopping Recording");
        try {
            mRecorder!!.stop()
            mRecorder!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
            setResult(RESULT_OK, null);
            finish()
        }
        mRecorder = null
        //starting the chronometer
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        //showing the play button
        Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show()

        var data = Intent();
        data.data = Uri.fromFile(File(fileName))
        data.putExtra("AUDIO_FILENAME", fileName);
        setResult(RESULT_OK, data);

        finish()
    }

}