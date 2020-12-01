package com.example.buff_guy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chart_Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String num;
    private int number;
    private ArrayList entries = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_);
        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref0 = database.getReference("Users").child(uid).child("SelectedGoal").child("GoalKcal");
        DatabaseReference ref1 = database.getReference("Users").child(uid).child("history").child("Kcal");
        DatabaseReference ref2 = database.getReference("Users").child(uid).child("SelectedGoal").child("GoalTime");
        DatabaseReference ref3 = database.getReference("Users").child(uid).child("history").child("Time");



        ref0.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                num = snapshot.getValue().toString();
                number = Integer.parseInt(num);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



/*
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String num = snapshot.getValue().toString();
                int number = Integer.parseInt(num);
                entries.add(new BarEntry(Float.valueOf(number),1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entries.add(new BarEntry(Integer.parseInt(snapshot.getValue().toString()),2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entries.add(new BarEntry(Integer.parseInt(snapshot.getValue().toString()),3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button btn = findViewById(R.id.btnn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


*/
        entries.add(new BarEntry(100,0));
        entries.add(new BarEntry(200,1));
        entries.add(new BarEntry(300,2));
        entries.add(new BarEntry(400,3));
        BarChart barChart = findViewById(R.id.barchart);

        ArrayList year = new ArrayList();

        year.add("목표 운동량");
        year.add("실제 운동량");
        year.add("목표 운동시간");
        year.add("실제 운동시간");

        BarDataSet barDataSet = new BarDataSet(entries,"UserData");
        barChart.animateY(3000);
        BarData data = new BarData(year,barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);





    }
}
