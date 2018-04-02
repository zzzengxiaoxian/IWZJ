package common.calender;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.iwzj.ltkj.iwzj.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CompactCalendarTab extends Fragment {

    private static final String TAG = "MainActivity";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private boolean shouldShow = false;
    private CompactCalendarView compactCalendarView;
    private ActionBar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_tab,container,false);

        final List<String> mutableBookings = new ArrayList<>();

        final ListView bookingsListView = (ListView) v.findViewById(R.id.bookings_listview);
        final ImageView showPreviousMonthBut = (ImageView) v.findViewById(R.id.prev_button);
        final ImageView showNextMonthBut = (ImageView) v.findViewById(R.id.next_button);
//        final Button slideCalendarBut = (Button) v.findViewById(R.id.slide_calendar);
        final ImageView showCalendarWithAnimationBut = (ImageView) v.findViewById(R.id.show_with_animation_calendar);
//        final Button setLocaleBut = (Button) v.findViewById(R.id.set_locale);
//        final Button removeAllEventsBut = (Button) v.findViewById(R.id.remove_all_events);

        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mutableBookings);
        bookingsListView.setAdapter(adapter);
        compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compactcalendar_view);

        // below allows you to configure color for the current day in the month
        // compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.black));
        // below allows you to configure colors for the current day the user has selected
        // compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));

        //使用中文字头 代表星期一星期二等
        Locale locale =Locale.CHINESE;
        dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", locale);
        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        dateFormatForDisplaying.setTimeZone(timeZone);
        dateFormatForMonth.setTimeZone(timeZone);
        compactCalendarView.setLocale(timeZone, locale);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        //compactCalendarView属性调用完之后 加载以下几个方法。才能显示
        loadEvents();
        loadEventsForYear(2017);
        compactCalendarView.invalidate();

        logEventsByMonth(compactCalendarView);

        // below line will display Sunday as the first day of the week
        // compactCalendarView.setShouldShowMondayAsFirstDay(false);

        // disable scrolling calendar
        // compactCalendarView.shouldScrollMonth(false);

        //set initial title
        toolbar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                toolbar.setTitle(dateFormatForMonth.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if(bookingsFromMap != null){
                    Log.d(TAG, bookingsFromMap.toString());
                    mutableBookings.clear();
                    for(Event booking : bookingsFromMap){
                        mutableBookings.add((String)booking.getData());
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });

        /*日历直接滑动滑出没动画效果*/
//        slideCalendarBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (shouldShow) {
//                    compactCalendarView.showCalendar();
//                } else {
//                    compactCalendarView.hideCalendar();
//                }
//                shouldShow = !shouldShow;
//            }
//        });

        /*日历滑动画出带动画效果*/
        showCalendarWithAnimationBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shouldShow) {
                    compactCalendarView.showCalendarWithAnimation();
                } else {
                    compactCalendarView.hideCalendarWithAnimation();
                }
                shouldShow = !shouldShow;
            }
        });

        /*日历更换时区，可以更换成世界上其他时区和文字*/
//        setLocaleBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Locale locale = new Locale("hi", "CN");
//                dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", locale);
////                TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
//                TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
//                dateFormatForDisplaying.setTimeZone(timeZone);
//                dateFormatForMonth.setTimeZone(timeZone);
//                compactCalendarView.setLocale(timeZone, locale);
//                compactCalendarView.setUseThreeLetterAbbreviation(true);
//                loadEvents();
//                loadEventsForYear(2017);
//                logEventsByMonth(compactCalendarView);
//
//            }
//        });

        /*移除有的日历中的时间的方法*/
//        removeAllEventsBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                compactCalendarView.removeAllEvents();
//            }
//        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        // Set to current day on resume to set calendar to latest day
        // toolbar.setTitle(dateFormatForMonth.format(new Date()));
    }

    private void loadEvents() {
        addEvents(-1, -1);
        addEvents(Calendar.DECEMBER, -1);
        addEvents(Calendar.AUGUST, -1);
    }

    private void loadEventsForYear(int year) {
        addEvents(Calendar.DECEMBER, year);
        addEvents(Calendar.AUGUST, year);
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.AUGUST);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }
        Log.d(TAG, "Events for Aug with simple date formatter: " + dates);
        Log.d(TAG, "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }

    private void addEvents(int month, int year) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < 6; i++) {
            currentCalender.setTime(firstDayOfMonth);
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }
            if (year > -1) {
                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                currentCalender.set(Calendar.YEAR, year);
            }
            currentCalender.add(Calendar.DATE, i);
            setToMidnight(currentCalender);
            long timeInMillis = currentCalender.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis, i);

            compactCalendarView.addEvents(events);
        }
    }

    /*日历获取时间发放，显示在listview中*/
    private List<Event> getEvents(long timeInMillis, int day) {
        if (day < 2) {
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
        } else if ( day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis) ),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
        }
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}