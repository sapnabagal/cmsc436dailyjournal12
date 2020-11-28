package com.example.dailyjournal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.utils.Size
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

public lateinit var addFab: FloatingActionButton
public lateinit var audioFab: FloatingActionButton
public lateinit var mediaFab: FloatingActionButton
public lateinit var textFab: FloatingActionButton
public var allFabsVisible: Boolean = false

private var selectedDate = LocalDate.now()

private val dateFormatter = DateTimeFormatter.ofPattern("dd")
private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
private val monthFormatter = DateTimeFormatter.ofPattern("MMM")



private lateinit var calendarView: CalendarView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calendarView = findViewById<CalendarView>(R.id.exSevenCalendar)
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

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
       calendarView.apply{
           val dayWidth = dm.widthPixels / 5
           val dayHeight = (dayWidth * 1.25).toInt()
           daySize = Size(dayWidth,dayHeight)

       }
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(3)
        val lastMonth = currentMonth.plusMonths(3)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek


        class DayViewContainer(view: View) : ViewContainer(view) {
            val dateText = view.findViewById<TextView>(R.id.dateCalendar)
            val monthText = view.findViewById<TextView>(R.id.monthCalendar)
            val dayText = view.findViewById<TextView>(R.id.dayCalendar)
            val selectView = view.findViewById<View>(R.id.selectCalendar)
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
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


                // With ViewBinding
                //val textView = CalendarDayLayoutBinding.bind(view).exTwoDayText
        }
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.bind(day)
            }
        }
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)
    }
}