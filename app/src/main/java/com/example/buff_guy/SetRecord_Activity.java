package com.example.buff_guy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SetRecord_Activity  extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgoal);

        firebaseAuth = firebaseAuth.getInstance();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();//

        Button backBtn = findViewById(R.id.BackBtn);
        Button OKBtn = findViewById(R.id.OKBtn);

        final RadioButton level_strong = findViewById(R.id.radioButton14);
        final RadioButton level_weak = findViewById(R.id.radioButton16);
        final RadioButton prefer_body = findViewById(R.id.radioButton6);
        final RadioButton prefer_tool = findViewById(R.id.radioButton8);
        final RadioButton prefer_up = findViewById(R.id.radioButton9);
        final RadioButton prefer_down = findViewById(R.id.radioButton10);
        final RadioButton purpose_health = findViewById(R.id.radioButton12);
        final RadioButton purpose_vulk = findViewById(R.id.radioButton13);
        final EditText GoalTimeEdt = findViewById(R.id.GoalTimeEdt);
        final EditText GoalKcalEdt = findViewById(R.id.GoalKcalEdt);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        OKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();
                HashMap<Object,String> hashMap = new HashMap<>();
                HashMap<Object,String> hashMap1 = new HashMap<>();
                hashMap1.put("GoalTime",GoalTimeEdt.getText().toString());
                hashMap1.put("GoalKcal",GoalKcalEdt.getText().toString());


                if (level_strong.isChecked()){
                    hashMap.put("운동강도","강");
                }
                if(level_weak.isChecked()){
                    hashMap.put("운동강도","약");
                }
                if(prefer_body.isChecked()){
                    hashMap.put("선호운동","맨몸운동");
                }
                if (prefer_tool.isChecked()){
                    hashMap.put("선호운동","기구운동");
                }
                if (prefer_up.isChecked()){
                    hashMap.put("선호부위","상체");
                }
                if (prefer_down.isChecked()){
                    hashMap.put("선호부위","하체");
                }
                if (purpose_health.isChecked()){
                    hashMap.put("운동목적","건강유지");
                }
                if (purpose_vulk.isChecked()){
                    hashMap.put("운동목적","벌크업");
                }


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Users");
                reference.child(uid).child("Goal").setValue(hashMap);
                reference.child(uid).child("SelectedGoal").setValue(hashMap1);

                Intent intent = new Intent(SetRecord_Activity.this,Home_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void SaveGoal(){
        //체크박스 항목을 찾아서 DB에 저장하는 함수


    }


}
