package com.example.buff_guy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Exercise_Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ImageView gifVIew;
    private ImageButton Back;
    private Intent intent;
    private TextView titleView;
    private TextView ExNameView;
    private int count;
    private ImageButton post;
    private ImageButton pre;
    private int Kcal;
    private int Count;
    private int Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_);

        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String uid = user.getUid();

        count = 0;

        intent = getIntent();
        final String course = intent.getStringExtra("코스");
        Kcal = Integer.parseInt(intent.getStringExtra("Kcal"));
        Count = Integer.parseInt(intent.getStringExtra("count"));
        Time = Integer.parseInt(intent.getStringExtra("Time"));

        titleView = findViewById(R.id.TitleView);
        titleView.setText(course+" 과정");

        ExNameView = findViewById(R.id.ExNameView);

        Back = findViewById(R.id.BackBtn);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise_Activity.this,Home_Activity.class);
                startActivity(intent);
                finish();
                if (course.equals("복근초급")&& count>=8){
                    int num = count/8;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Users").child(uid).child("history");

                    HashMap<Object,String> map = new HashMap<>();
                    map.put("Kcal",Integer.toString((Kcal+(100*num))));
                    map.put("Time",Integer.toString((Time + (20*num))));
                    map.put("count",Integer.toString((Count+(1*num))));

                    reference.setValue(map);

                }
            }
        });



        gifVIew = findViewById(R.id.GifView);
        setViewData(course);

        post = findViewById(R.id.PostExBtn);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count -= 1;
                if (count < 0){
                    count = 0;
                }
                setViewData(course);
            }
        });

        pre = findViewById(R.id.PreExBtn);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                setViewData(course);
            }
        });
        //Glide.with(this).load(R.drawable.abs_mountain).into(gifVIew);
    }


public void setViewData(String courseName){

        if (courseName.equals("복근초급")) {
            String[] arr = getResources().getStringArray(R.array.복근초급);
            if(count % 8 == 0){
                ExNameView.setText(arr[0]);
                Glide.with(this).load(R.drawable.all_jumpingjack).into(gifVIew);
            }
            if(count % 8 == 1){
                ExNameView.setText(arr[1]);
                Glide.with(this).load(R.drawable.abs_scissors).into(gifVIew);
            }
            if(count % 8 == 2){
                ExNameView.setText(arr[2]);
                Glide.with(this).load(R.drawable.abs_mountain).into(gifVIew);
            }
            if(count % 8 == 3){
                ExNameView.setText(arr[3]);
                Glide.with(this).load(R.drawable.abs_legup).into(gifVIew);
            }
            if(count % 8 == 4){
                ExNameView.setText(arr[4]);
                Glide.with(this).load(R.drawable.abs_plank).into(gifVIew);
            }
            if(count % 8 == 5){
                ExNameView.setText(arr[5]);
                Glide.with(this).load(R.drawable.abs_scissors).into(gifVIew);
            }
            if(count % 8 == 6){
                ExNameView.setText(arr[6]);
                Glide.with(this).load(R.drawable.abs_mountain).into(gifVIew);
            }
            if(count % 8 == 7){
                ExNameView.setText(arr[7]);
                Glide.with(this).load(R.drawable.abs_legup).into(gifVIew);
            }
        }
}
}
