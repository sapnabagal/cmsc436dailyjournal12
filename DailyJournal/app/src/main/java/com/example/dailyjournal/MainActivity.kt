package com.example.dailyjournal

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailyjournal.databinding.ActivityCalendarBinding
import com.example.dailyjournal.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
        val date = requireArguments().getString("date")
        selectedDate = LocalDate.parse(date)
        binding =ActivityMainBinding.bind(view)
<<<<<<< HEAD
=======

>>>>>>> 9a73d3fb6b3536f2a4c6427349df2a0bf07a5b09
        super.onCreate(savedInstanceState)
        //adds a event to the first day
        //events[selectedDate] = events[selectedDate].orEmpty().plus(TextType("THIS IS A INIT TEST", selectedDate))
        recycler_view.adapter = eventAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
<<<<<<< HEAD
=======

>>>>>>> 9a73d3fb6b3536f2a4c6427349df2a0bf07a5b09
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
        textFab.setOnClickListener {
            //TODO: create text editor activity to edit text
            var newText : String = "";
            try{
<<<<<<< HEAD
                var textEditText = EditText(requireContext());
                val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(requireContext())
=======
                var textEditText = EditText(activity);
                val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(activity)
>>>>>>> 9a73d3fb6b3536f2a4c6427349df2a0bf07a5b09
                        .setTitle("Add a new text entry")
                        .setMessage("What did you do today?")
                        .setView(textEditText)
                        .setPositiveButton("Add") { dialog, which ->
                            val text: String = textEditText.text.toString()
                            newText = text;
                            events[selectedDate] = events[selectedDate].orEmpty().plus(TextType(text, selectedDate))
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
        audioFab.setOnClickListener {
            //TODO: open activity to record audio

            events[selectedDate] = events[selectedDate].orEmpty().plus(AudioType(selectedDate))
            updateAdapterForDate(selectedDate)
            audioFab.hide()
            mediaFab.hide()
            textFab.hide()
            videoFab.hide()
            allFabsVisible = false

        }
        mediaFab.setOnClickListener{
            //TODO: open activity to upload photo
            //this will create either a audio or video data entry depending on what the user selects

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
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Pick an image"), PICK_IMAGE_ROM_GALLERY)

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
                    intent.setAction(Intent.ACTION_GET_CONTENT);
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

                    dateText.setTextColor(view.context.getColor(if (day.date == selectedDate) R.color.pink else R.color.black))
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
    private fun updateAdapterForDate(date: LocalDate) {
        //this function is used to clear the recycler view when a new date is selected then add the events from that day
        eventAdapter.apply {
            dataList.clear()
            dataList.addAll(this@MainActivity.events[date].orEmpty())
            notifyDataSetChanged()
        }
    }

    private fun deleteEvent(data :  ListItem){
        val date = data.getDate()
        events[date] = events[date].orEmpty().minus(data)
        updateAdapterForDate(date)


    }
    override fun onItemDelete(data : ListItem) {
        //TODO: determine what the item data is based on the ListItemType then create a dialgoue to view it, or delete it
        // possibly add edit for text but idk
        //Toast.makeText(this@MainActivity, data.getListItemType().toString(), Toast.LENGTH_SHORT).show()
        //if(data.getListItemType() == ListItem.TYPE_TEXT){
        //    (data as TextType).inputText = Calendar.getInstance().time.toString()
        //    eventAdapter.notifyDataSetChanged()
        //}
        deleteEvent(data)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "in onActivity Result $resultCode")
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_ROM_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "image result OK")
                    if (data == null) {
                        Log.i(TAG, "data returned is null")
                        return;
                    }
                    var imageData : Uri = data.data!!;
                    events[selectedDate] = events[selectedDate].orEmpty().plus(PicType(imageData, selectedDate))
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
                    events[selectedDate] = events[selectedDate].orEmpty().plus(VidType(videoData, selectedDate))
                    updateAdapterForDate(selectedDate)
                }
            }
        }
    }

    companion object {

        private val TAG = "Get-Image"
        val PICK_IMAGE_ROM_GALLERY = 2
        val PICK_VIDEO_FROM_GALLERY = 3
        val RES_IMAGE = 100
        var INTENT_DATA = "course.labs.locationlab.placerecord.IntentData"

    }


    override fun onItemEdit(data: ListItem) {
        //TODO make a activity to edit the data
        Toast.makeText(activity,"EDITING",Toast.LENGTH_SHORT).show();
    }

    override fun onItemClick(data: ListItem) {
        //TODO make a activity to preview the data
        Toast.makeText(activity,"PREVIEW",Toast.LENGTH_SHORT).show();
    }


}
