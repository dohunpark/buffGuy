package com.example.buff_guy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String value =" ";
    private Spinner sp1;
    private Spinner sp2;
    private TextView nameView1;
    private TextView nameView2;
    private String[] arr;
    private TextView ExCount;
    private TextView KcalCount;
    private TextView TimeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        arr = new String[3];

        ExCount = findViewById(R.id.CountNumView);
        KcalCount = findViewById(R.id.KcalNumView);
        TimeCount = findViewById(R.id.MinNumView);
        nameView1 = findViewById(R.id.ClassNameVIew1st);
        nameView2 = findViewById(R.id.ClassNameView2nd);
        Button GoalBtn = findViewById(R.id.GoalBtn);
        Button GoBtn1 = findViewById(R.id.GoBtn1);
        Button GoBtn2 = findViewById(R.id.GoBtn2);
        Button ChkRec = findViewById(R.id.RecordChk);
        sp1 = findViewById(R.id.ClassSpinner1st);
        sp2 = findViewById(R.id.ClassSpinner2nd);


        SetExercise(uid);



        GoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SetRecord_Activity.class);
                startActivity(intent);
            }
        });
        // 목표설정 버튼

        GoBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Exercise_Activity.class);
                intent.putExtra("코스",nameView1.getText().toString());
                intent.putExtra("Kcal",arr[1]);
                intent.putExtra("count",arr[0]);
                intent.putExtra("Time",arr[2]);
                startActivity(intent);
                //추후에는 데이터도 넘겨라
            }
        });
        //운동하기 버튼 1

        GoBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Exercise_Activity.class);
                intent.putExtra("코스",nameView2.getText().toString());
                startActivity(intent);
                //추후에는 데이터도 넘겨라
            }
        });
        //운동하기 버튼2

        ChkRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Chart_Activity.class);
                startActivity(intent);
                //추후에는 데이터도 넘겨라
            }
        });

    }

    public void SetExercise(final String uid){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(uid).child("Goal");
        DatabaseReference reference = database.getReference("Users").child(uid).child("history");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value = snapshot.getValue().toString();

                SetSpinner(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr[0] = snapshot.child("count").getValue().toString();
                arr[1] = snapshot.child("Kcal").getValue().toString();
                arr[2] = snapshot.child("Time").getValue().toString();
                SetTxtViews(arr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void SetTxtViews(String[] arr){
        ExCount.setText(arr[0]);
        KcalCount.setText(arr[1]);
        TimeCount.setText(arr[2]);
    }

    public void SetSpinner(String value){

        if (value.equals("{운동목적=벌크업, 선호부위=상체, 선호운동=맨몸운동, 운동강도=약}")){
            String[] arr1 = getResources().getStringArray(R.array.복근초급);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr1);
            String[] arr2 = getResources().getStringArray(R.array.가슴초급);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr2);
            sp1.setAdapter(adapter1);
            nameView1.setText("복근초급");
            sp2.setAdapter(adapter2);
            nameView2.setText("가슴초급");
        }
        else if(value.equals("{운동목적=벌크업, 선호부위=상체, 선호운동=맨몸운동, 운동강도=강}")){
            String[] arr1 = getResources().getStringArray(R.array.복급고급);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr1);
            String[] arr2 = getResources().getStringArray(R.array.가슴고급);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr2);
            sp1.setAdapter(adapter1);
            nameView1.setText("복근고급");
            sp2.setAdapter(adapter2);
            nameView2.setText("가슴고급");
        }



    }
}
