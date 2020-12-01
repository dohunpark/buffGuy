package com.example.buff_guy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SingUp_Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_);

        firebaseAuth= FirebaseAuth.getInstance();;
        final EditText IDedt = findViewById(R.id.IDEdt);
        final EditText PWedt = findViewById(R.id.PWEdt);



        Button backBtn = findViewById(R.id.BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button signup_btn = findViewById(R.id.SignUpBtn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String User_id = IDedt.getText().toString().trim();
                String User_pw = PWedt.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(User_id,User_pw).addOnCompleteListener(SingUp_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String id = user.getEmail();
                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("uid",uid);
                            hashMap.put("id",id);

                            HashMap<Object,String> hm = new HashMap<>();
                            hm.put("운동강도","강");
                            hm.put("선호운동","맨몸운동");
                            hm.put("선호부위","상체");
                            hm.put("운동목적","건강유지");

                            HashMap<Object,String> map = new HashMap<>();
                            map.put("Kcal","0");
                            map.put("Time","0");
                            map.put("count","0");

                            HashMap<Object,String> Goal = new HashMap<>();
                            Goal.put("Time","0");
                            Goal.put("Kcal","0");


                            HashMap<Object,String> hashMap1 = new HashMap<>();
                            hashMap1.put("GoalTime","0");
                            hashMap1.put("GoalKcal","0");


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);
                            reference.child(uid).child("Goal").setValue(hm);
                            reference.child(uid).child("history").setValue(map);
                            //reference.child(uid).child("date").setValue(Goal);
                            reference.child(uid).child("SelectedGoal").setValue(hashMap1);

                            Intent intent = new Intent(SingUp_Activity.this,Login_Activity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(SingUp_Activity.this, "회원가입 성공!",Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(SingUp_Activity.this, "회원가입 실패!",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
            }
        });
    }


}
