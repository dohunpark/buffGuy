package com.example.buff_guy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login_Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        firebaseAuth = firebaseAuth.getInstance();

        final Button Login_btn = (Button)findViewById(R.id.LoginBtn);
        TextView findPwView = findViewById(R.id.FindPwView);
        TextView findIdView =  findViewById(R.id.FindIDView);
        TextView signup =findViewById(R.id.SignUpView);
        final EditText id = findViewById(R.id.IDedt);
        final EditText pw =findViewById(R.id.PWedt);

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_id = id.getText().toString().trim();
                String input_pw = pw.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(input_id,input_pw).addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(Login_Activity.this,Home_Activity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Login_Activity.this,"ID와 PW를 확인해 주세요.",Toast.LENGTH_SHORT).show();;
                        }
                    }
                });
            }
        });
        //로그인 버튼 관련

        findIdView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.find_id,null);
                builder.setView(layout);
                final EditText IDedt = (EditText)layout.findViewById(R.id.IDEdt);
                final EditText PWedt = (EditText)layout.findViewById(R.id.PWEdt);
                final EditText PhoneNumedt = (EditText)layout.findViewById(R.id.PhoneNumedt);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String User_id = IDedt.getText().toString().trim();
                        final String User_pw = PWedt.getText().toString().trim();
                        final String Phone_num = PhoneNumedt.getText().toString().trim();

                        firebaseAuth.createUserWithEmailAndPassword(User_id,User_pw).addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String id = user.getEmail();
                                    String uid = user.getUid();


                                    HashMap<Object, String> hashMap = new HashMap<>();
                                    hashMap.put("uid",uid);
                                    hashMap.put("id",id);

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference reference = database.getReference("Users");
                                    reference.child(uid).setValue(hashMap);

                                    //Intent intent = new Intent(Login_Activity.this,Login_Activity.class);
                                    //startActivity(intent);
                                    //finish();

                                    Toast.makeText(Login_Activity.this, "회원가입 성공!",Toast.LENGTH_LONG).show();

                                }
                                else{
                                    Toast.makeText(Login_Activity.this, "회원가입 실패!",Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });
                        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, null);

                builder.create().show();

            }
        });
        //아이디 찾기 관련

        findPwView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.find_pw,null);
                builder.setView(layout);
                final EditText findpwedt = (EditText)layout.findViewById(R.id.IDedt);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = findpwedt.getText().toString().trim();
                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Login_Activity.this, "이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                                }else{
                                    Toast.makeText(Login_Activity.this,"이메일을 다시 확인해주세요.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, null);

                builder.create().show();
            }
        });
        //비번 찾기 관련

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SingUp_Activity.class);
                startActivity(intent);
            }
        });


    }


}
