package com.example.buff_guy;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BreakTime_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_time_);

        final TextView timer = (TextView)findViewById(R.id.TimerView);
        timer.setTextSize(50);
        final TextView next = (TextView)findViewById(R.id.NextExView);
        next.setTextSize(25);
        Button nextBtn = findViewById(R.id.NextBtn);

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(String.format(Locale.getDefault(), "%d sec.", millisUntilFinished / 1000L));
                next.setText("다음운동은 XXX입니다.");
            }

            public void onFinish() {
                timer.setText("Done.");
            }
        }.start();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
