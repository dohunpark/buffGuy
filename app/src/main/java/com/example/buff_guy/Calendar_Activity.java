package com.example.buff_guy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Collections;

public class Calendar_Activity extends AppCompatActivity {

    private MaterialCalendarView matCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_);

        Button btn = findViewById(R.id.BackBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       matCal = findViewById(R.id.calendarView);

        matCal.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
                //new OneDayDecorator()
                //new EventDecorator(Color.RED, Collections.singleton(CalendarDay.today()))


        );

        setDot();

    }

    public void setDot(){


        for (int i = 6; i <8; i++){
            matCal.addDecorators(
                    new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(2020, Calendar.NOVEMBER, i)))

            );


        }
    }
}
