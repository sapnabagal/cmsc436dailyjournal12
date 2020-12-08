package com.example.dailyjournal

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailyjournal.database.AppDatabase
import com.example.dailyjournal.database.DataItemDao
import com.example.dailyjournal.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class MainActivity : Fragment(R.layout.activity_main), OnItemClickListener{
    lateinit var addFab: FloatingActionButton
    lateinit var audioFab: FloatingActionButton
    lateinit var mediaFab: FloatingActionButton
    lateinit var videoFab: FloatingActionButton
    lateinit var textFab: FloatingActionButton
    var allFabsVisible: Boolean = false

    private lateinit var selectedDate : LocalDate
    private var imageUri: Uri? = null
    private var imgPath: String = ""
    private lateinit var placeIntent : Intent

    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    private lateinit var calendarView: CalendarView
    private val events = mutableMapOf<LocalDate, List<ListItem>>()
    private var eventAdapter = Adapter(this)
    private lateinit var binding: ActivityMainBinding

    private lateinit var mPlayer: MediaPlayer

    private lateinit var dataItemDao: DataItemDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val appDatabase = AppDatabase.getInstance(requireActivity().applicationContext)
        dataItemDao = appDatabase.dataItemDao()

        //binding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
        val date = requireArguments().getString("date")
        selectedDate = LocalDate.parse(date)
        binding =ActivityMainBinding.bind(view)
        super.onCreate(savedInstanceState)
        //adds a event to the first day
        //events[selectedDate] = events[selectedDate].orEmpty().plus(TextType("THIS IS A INIT TEST", selectedDate))
        var mDividerItemDecoration = DividerItemDecoration(recycler_view.context, DividerItemDecoration.VERTICAL)
        recycler_view.addItemDecoration(mDividerItemDecoration)
        recycler_view.adapter = eventAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())

        calendarView = binding.exSevenCalendar
        addFab = binding.fab
        audioFab = binding.audio
        textFab= binding.text
        mediaFab= binding.media
        videoFab= binding.video

        audioFab.visibility = View.GONE
        mediaFab.visibility = View.GONE
        textFab.visibility = View.GONE
        videoFab.visibility = View.GONE
        addFab.setOnClickListener{
            if(!allFabsVisible){
                audioFab.show()
                mediaFab.show()
                textFab.show()
                videoFab.show()
                allFabsVisible = true
            }else{
                audioFab.hide()
                mediaFab.hide()
                textFab.hide()
                videoFab.hide()
                allFabsVisible = false


            }
        }

        /* Opens an AlertDialog to allow user to enter a text entry into the journal */
        textFab.setOnClickListener {
            //TODO: create text editor activity to edit text
            var newText : String = "";
            try{
                var textEditText = EditText(activity);
                val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(activity)
                        .setTitle("Add a new text entry")
                        .setMessage("What did you do today?")
                        .setView(textEditText)
                        .setPositiveButton("Add") { dialog, which ->
                            val text: String = textEditText.text.toString()
                            newText = text;
                            //events[selectedDate] = events[selectedDate].orEmpty().plus(TextType(text, selectedDate, LocalTime.now()))
                            dataItemDao.insertAll(TextType(text, selectedDate, LocalTime.now()))
                            updateAdapterForDate(selectedDate)
                        }
                        .setNegativeButton("Cancel", null)
                        .create()
                dialog!!.show()
            }
            catch(e: Exception){
                e.printStackTrace();
            }

            //events[selectedDate] = events[selectedDate].orEmpty().plus(TextType("test text", selectedDate))
            //events[selectedDate] = events[selectedDate].orEmpty().plus(TextType(newText, selectedDate))
            //updateAdapterForDate(selectedDate)
            audioFab.hide()
            mediaFab.hide()
            textFab.hide()
            videoFab.hide()
            allFabsVisible = false

        }

        /* Starts the AudioActivity upon permission granted for recording audio, reading/writing to external storage.
        * Audio Activity is responsible for recording the voice memo, will be played in on item click back in MainActivity. */
        audioFab.setOnClickListener {
            //TODO: open activity to record audio

            try {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) !== PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            arrayOf<String>(
                                    Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ),
                            RECORD_AUDIO
                    )
                } else {

                    var intent = Intent(activity, AudioActivity::class.java);
                    Log.i(TAG, "starting audio activity")
                    startActivityForResult(intent, RECORD_AUDIO)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //events[selectedDate] = events[selectedDate].orEmpty().plus(AudioType(Uri.EMPTY, "test audio string", selectedDate))
            //updateAdapterForDate(selectedDate)
            audioFab.hide()
            mediaFab.hide()
            textFab.hide()
            videoFab.hide()
            allFabsVisible = false

        }

        /* Starts the select from gallery intent to allow user to select only an image type to add as an entry.
        * Saves result as a Uri object which is part of the result intent. */
        mediaFab.setOnClickListener{

            try {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf<String>(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        PICK_IMAGE_ROM_GALLERY
                    )
                } else {

                    var intent = Intent()
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    startActivityForResult(Intent.createChooser(intent, "Pick an image"), PICK_IMAGE_ROM_GALLERY)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            updateAdapterForDate(selectedDate)
            audioFab.hide()
            mediaFab.hide()
            textFab.hide()
            videoFab.hide()
            allFabsVisible = false
        }


        /* Starts the gallery choose activity for user to select a video file.
         * Saves the Uri as part of the intent result to display later  */

        videoFab.setOnClickListener{
            //TODO: open activity to upload video
            //this will create either a audio or video data entry depending on what the user selects

            try {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf<String>(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        PICK_VIDEO_FROM_GALLERY
                    )
                } else {

                    var intent = Intent()
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    startActivityForResult(Intent.createChooser(intent, "Pick a video"), PICK_VIDEO_FROM_GALLERY)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //events[selectedDate] = events[selectedDate].orEmpty().plus(PicType(R.drawable.media))
            updateAdapterForDate(selectedDate)
            audioFab.hide()
            mediaFab.hide()
            textFab.hide()
            videoFab.hide()
            allFabsVisible = false
        }

        //this is for the calendar day UI size
        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        var dayWidth : Int
        var dayHeight: Int
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            dayWidth = dm.widthPixels / 10
            dayHeight = (dayWidth * 1.25).toInt()
        }else{
            dayWidth = dm.widthPixels / 5
            dayHeight = (dayWidth * 1.25).toInt()

        }
        calendarView.apply{
            daySize = Size(dayWidth,dayHeight)
       }
        if(savedInstanceState == null){
            //this is where the recyclerView gets updated
            //initializing the database for today's date would go here
            calendarView.post{
                updateAdapterForDate(selectedDate)
            }
        }


        class DayViewContainer(view: View) : ViewContainer(view) {
            val dateText = view.findViewById<TextView>(R.id.dateCalendar)
            val monthText = view.findViewById<TextView>(R.id.monthCalendar)
            val dayText = view.findViewById<TextView>(R.id.dayCalendar)
            val selectView = view.findViewById<View>(R.id.selectCalendar)
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    updateAdapterForDate(day.date)
                    val firstDay = calendarView.findFirstVisibleDay()
                    val lastDay = calendarView.findLastVisibleDay()
                    if (firstDay == day) {
                        // If the first date on screen was clicked, we scroll to the date to ensure
                        // it is fully visible if it was partially off the screen when clicked.
                        calendarView.smoothScrollToDate(day.date)
                    } else if (lastDay == day) {
                        // If the last date was clicked, we scroll to 4 days ago, this forces the
                        // clicked date to be fully visible if it was partially off the screen.
                        // We scroll to 4 days ago because we show max of five days on the screen
                        // so scrolling to 4 days ago brings the clicked date into full visibility
                        // at the end of the calendar view.
                        calendarView.smoothScrollToDate(day.date.minusDays(4))
                    }

                    //calendarView.smoothScrollToDate(day.date.minusDays(0))
                    // Example: If you want the clicked date to always be centered on the screen,
                    // you would use: exSevenCalendar.smoothScrollToDate(day.date.minusDays(2))

                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        calendarView.notifyDateChanged(day.date)
                        oldDate?.let { calendarView.notifyDateChanged(it) }
                    }
                    }
                }
                fun bind(day: CalendarDay){
                    this.day = day
                    dateText.text = dateFormatter.format(day.date)
                    dayText.text = dayFormatter.format(day.date)
                    monthText.text = monthFormatter.format(day.date)

                    dateText.setTextColor(view.context.getColor(if (day.date == selectedDate) R.color.pink else R.color.white))
                    selectView.isVisible = day.date == selectedDate

                }
        }

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.bind(day)
            }
        }
        val currentMonth = YearMonth.from(selectedDate)
        val lastMonth = currentMonth.plusMonths(0)
        //val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek.rand
        calendarView.setup(currentMonth, lastMonth, DayOfWeek.values().random())
        calendarView.scrollToDate(selectedDate)
        //calendarView.scrollToDate(LocalDate.now())
    }

    /* this function is used to clear the recycler view when a new date is selected then add the events from that day */
    private fun updateAdapterForDate(date: LocalDate) {

        eventAdapter.apply {
            dataList.clear()
            //dataList.addAll(this@MainActivity.events[date].orEmpty())
            dataList.addAll(dataItemDao.loadAllByDateListItem(date))
            notifyDataSetChanged()
        }
    }

    /* Deletes entry from database */

    private fun deleteEvent(data :  ListItem){
        val date = data.getDate()
        //events[date] = events[date].orEmpty().minus(data)
        dataItemDao.delete(data)
        updateAdapterForDate(date)


    }
    override fun onItemDelete(data : ListItem) {
        //removes the selected list item data from recyclerview and database.

        deleteEvent(data)

    }

    /* Responding to each of the result codes from respective activities launched.
    *
    * PICK_IMAGE_FROM_GALLERY -- retrieves imageUri data from select from gallery activity launched
    * PICK_VIDEO_FROM_GALLERY -- retrieves videoUri data from select from gallery activity launched
    * RECORD_AUDIO -- retrieves filename data from recorded audio and stores audio in Intent extra
    *
    * */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "in onActivity Result $resultCode")

        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            /* Select Image from Gallery result code */
            PICK_IMAGE_ROM_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "image result OK")
                    if (data == null) {
                        Log.i(TAG, "data returned is null")
                        return;
                    }
                    /* Retrieving image Uri */
                    var imageData : Uri = data.data!!;

                    /* Inserting into the database as a PicType ListItem with local timestamp */
                    dataItemDao.insertAll(PicType(imageData, selectedDate, LocalTime.now()))
                    updateAdapterForDate(selectedDate)
                }
            }

            PICK_VIDEO_FROM_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "video result OK")
                    if (data == null) {
                        Log.i(TAG, "data returned is null")
                        return;
                    }
                    var videoData : Uri = data.data!!;
                    //events[selectedDate] = events[selectedDate].orEmpty().plus(VidType(videoData, selectedDate, LocalTime.now()))
                    dataItemDao.insertAll(VidType(videoData, selectedDate, LocalTime.now()))
                    updateAdapterForDate(selectedDate)
                }
            }
            RECORD_AUDIO->{
                if(resultCode== Activity.RESULT_OK){
                    Log.i(TAG, "audio result OK")
                    if(data == null){
                        Log.i(TAG, "data returned is null")
                        //events[selectedDate] = events[selectedDate].orEmpty().plus(AudioType("test audio string", selectedDate, LocalTime.now()))
                        updateAdapterForDate(selectedDate)
                        return;
                    }
                    var audioData : Uri = data.data!!;
                    var filename : String? = data.getStringExtra("AUDIO_FILENAME")

                    //events[selectedDate] = events[selectedDate].orEmpty().plus(AudioType(filename!!, selectedDate, LocalTime.now()))
                    dataItemDao.insertAll(AudioType(filename!!, selectedDate, LocalTime.now()))

                    updateAdapterForDate(selectedDate)
                }



            }
        }
    }

    override fun onItemEdit(data: ListItem) {
        //TODO make a activity to edit the data
        Toast.makeText(activity,"EDITING",Toast.LENGTH_SHORT).show();
    }

    /* Opens up a more focused view of each Entry
    *
    * Text -- opens AlertDialog with larger text
    * Image -- opens Full sized image for viewing
    * Video -- starts playing video on click
    * Audio -- starts playback of recorded audio in AlertDialog
    *
    * */

    override fun onItemClick(data: ListItem) {
        //TODO make a activity to preview the data
        Toast.makeText(activity,"PREVIEW",Toast.LENGTH_SHORT).show();
        if (data.getListItemType()==4){
            var image = data as PicType

            val alertadd = android.app.AlertDialog.Builder(activity)
            val factory = LayoutInflater.from(activity)
            val view: View = factory.inflate(R.layout.view_image, null)
            val imageView = view.findViewById<ImageView>(R.id.image_large)
            imageView.setImageURI(image.image)
            alertadd.setView(view)
            alertadd.setNeutralButton("Close") { dlg, sumthin -> }

            alertadd.show()
        }
        else if (data.getListItemType()==2){
            var vid = data as VidType

            val alertadd = android.app.AlertDialog.Builder(activity)
            val factory = LayoutInflater.from(activity)
            val view: View = factory.inflate(R.layout.view_video, null)
            val vidView = view.findViewById<VideoView>(R.id.video_large)
            vidView.setVideoURI(vid.video)
            vidView.start()
            alertadd.setView(view)
            alertadd.setNeutralButton("Close") { dlg, sumthin -> vidView.stopPlayback()}

            alertadd.show()
        }
        else if (data.getListItemType()==3){
            var text = data as TextType

            val alertadd = android.app.AlertDialog.Builder(activity)
            val factory = LayoutInflater.from(activity)
            val view: View = factory.inflate(R.layout.view_text, null)
            val textView = view.findViewById<TextView>(R.id.text_large)
            textView.text = text.inputText

            alertadd.setView(view)
            alertadd.setNeutralButton("Close") { dlg, sumthin -> }

            alertadd.show()
        }
        else if(data.getListItemType()==1){
            var audio = data as AudioType
            var fileName = data.audioFile

            val alertadd = android.app.AlertDialog.Builder(activity)
            val factory = LayoutInflater.from(activity)
            val view: View = factory.inflate(R.layout.view_audio, null)
            val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
            var imgViewPlay = view.findViewById<ImageView>(R.id.imgViewPlay)
            var lastProgress = 0;
            var isPlaying = false;

            /* imgView play is the playbutton in the view_audio xml file */
            imgViewPlay.setOnClickListener { v ->

                mPlayer = MediaPlayer()
                var mExecutor = Executors.newSingleThreadScheduledExecutor()

                var mSeekbarProgressUpdateTask = Runnable {
                    if (mPlayer != null
                            && mPlayer.isPlaying) {
                        val currentPosition: Int = mPlayer.currentPosition
                        seekBar.progress = currentPosition
                    }
                }


                try {
                    mPlayer!!.setDataSource(fileName)
                    mPlayer!!.prepare()
                    mPlayer!!.start()
                } catch (e: IOException) {
                    Log.e("LOG_TAG", "prepare() failed")
                }

                //making the imageView pause button

                imgViewPlay.setImageResource(R.drawable.ic_pause_circle)

                seekBar.progress = lastProgress
                mPlayer!!.seekTo(lastProgress)
                seekBar.max = mPlayer!!.duration
                mExecutor.scheduleAtFixedRate(mSeekbarProgressUpdateTask, 0, 100, TimeUnit.MILLISECONDS)

                mPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                    imgViewPlay.setImageResource(R.drawable.ic_play_circle)
                    mPlayer!!.seekTo(0)
                })

                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        if (mPlayer != null && fromUser) {
                            mPlayer!!.seekTo(progress)
                            lastProgress = progress
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar) {}
                })
            }

            alertadd.setView(view)
            alertadd.setNeutralButton("Close") { dlg, sumthin -> }

            alertadd.show()
        }
    }

    companion object {

        private val TAG = "Get-Image"
        val PICK_IMAGE_ROM_GALLERY = 2
        val PICK_VIDEO_FROM_GALLERY = 3
        var RECORD_AUDIO = 4;

    }

}
