package com.example.dailyjournal

import android.annotation.SuppressLint
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.example.dailyjournal.database.AppDatabase
import com.example.dailyjournal.database.DataItemDao
import com.example.dailyjournal.databinding.ActivityCalendarBinding
import com.example.dailyjournal.databinding.CalendarDay2Binding
import com.example.dailyjournal.databinding.CalendarMonthHeader2Binding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/* Activity to show monthly calendar view. Connects to Database to hold all individual day information */
class CalendarActivity : AppCompatActivity(){

    private lateinit var binding: ActivityCalendarBinding
    private var selectedDate : LocalDate? = null
    private val today = LocalDate.now()
    private lateinit var dataItemDao: DataItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appDatabase = AppDatabase.getInstance(this)
        dataItemDao = appDatabase.dataItemDao()
        //setContentView(R.layout.activity_calendar)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar)


        binding.overviewCalendar.setup(YearMonth.now(), YearMonth.now().plusMonths(10),DayOfWeek.values().first())

        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = CalendarDay2Binding.bind(view).exTwoDayText

            init {
                textView.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDate == day.date) {
                            selectedDate = null
                            binding.overviewCalendar.notifyDayChanged(day)
                        } else {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            binding.overviewCalendar.notifyDateChanged(day.date)
                            oldDate?.let { binding.overviewCalendar.notifyDateChanged(oldDate) }
                        }
                    }

                    val fragment = MainActivity()
                    val transaction: FragmentTransaction =
                        supportFragmentManager.beginTransaction()
                    val info = Bundle()
                    info.putString("date", day.date.toString())
                    fragment.arguments = info
                    transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out_down)
                    transaction.add(R.id.list_view_container, fragment, fragment.javaClass.simpleName)
                    transaction.addToBackStack(MainActivity.javaClass.simpleName)
                    transaction.commit()
                }
            }
        }

        binding.overviewCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.alpha = 1f
                    when (day.date) {
                        selectedDate -> {
                            textView.setTextColor(Color.parseColor("#FFFFFF"))
                            textView.setBackgroundResource(R.drawable.selected)
                        }
                        today -> {
                            textView.setTextColor(Color.parseColor("#ff0066"))
                            if(dataItemDao.dateExists(day.date)){
                                textView.setBackgroundResource(R.drawable.entry)
                            }else{
                                textView.background = null

                            }
                        }
                        else -> {
                            textView.setTextColor(Color.parseColor("#000000"))
                            if(dataItemDao.dateExists(day.date)){
                                textView.setBackgroundResource(R.drawable.entry)
                            }else{
                                textView.background = null

                            }
                        }

                    }
                } else {
                    textView.alpha = 0f
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarMonthHeader2Binding.bind(view).exTwoHeaderText
        }
        binding.overviewCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                @SuppressLint("SetTextI18n")
                container.textView.text = "${month.yearMonth.month.name.toLowerCase().capitalize()} ${month.year}"
            }
        }
    }
}