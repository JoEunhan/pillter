package com.nani.pillter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "SignupActivity";

    Context context = this;
    private FirebaseAuth mAuth;
    Button join_us;
    EditText name;
    EditText phone_number;
    EditText  email;
    EditText pass_word;
    EditText pwd_chk;

    Spinner sex;
    Spinner age;

    String sex_value;
    int age_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        join_us = (Button) findViewById(R.id.join_us);
        name = (EditText) findViewById(R.id.join_name);
        phone_number = (EditText) findViewById(R.id.join_number);
        email = (EditText) findViewById(R.id.join_email);
        pass_word = (EditText) findViewById(R.id.join_password);
        pwd_chk = (EditText) findViewById(R.id.join_passwordcheck);

        mAuth=FirebaseAuth.getInstance();

        sex = (Spinner) findViewById(R.id.join_sex);
        ArrayAdapter<CharSequence> a = ArrayAdapter.createFromResource(this, R.array.sex_type, android.R.layout.simple_spinner_item);
        a.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sex.setAdapter(a);

        age = (Spinner) findViewById(R.id.join_age);

        Integer[] agearray = new Integer[100];
        for (int i = 0; i < 100; i++) {
            agearray[i] = i + 1;
        }

        ArrayAdapter<Integer> b = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, agearray);
        b.setDropDownViewResource(android.R.layout.simple_spinner_item);
        age.setAdapter(b);

        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex_value=sex.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignupActivity.this,"성별 선택해 주세요.",Toast.LENGTH_SHORT).show();
            }
        });
        age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age_value=Integer.valueOf(age.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(SignupActivity.this,"나이를 선택해 주세요.",Toast.LENGTH_SHORT).show();
            }
        });
        join_us.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")||email.getText().toString().equals("")||
                        pass_word.getText().toString().equals("")||pwd_chk.getText().toString().equals("")||phone_number.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this,"입력하지 않은 부분이 있습니다.",Toast.LENGTH_SHORT).show();
                }else if(pass_word.getText().toString().length()<6)
                    Toast.makeText(SignupActivity.this,"비밀 번호를 6자리 이상 입력해주세요.",Toast.LENGTH_SHORT).show();
                else if (pwd_chk.getText().toString().trim().equals(pass_word.getText().toString().trim())) {
                    final ProgressDialog mDialog = new ProgressDialog(SignupActivity.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), pass_word.getText().toString().trim())
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "회원가입성공!");

                                      /*realtime DB에 정보를 올려 봅시다!*/
                                        FirebaseFirestore dt = FirebaseFirestore.getInstance();
                                        Map<String,Object> user = new HashMap<>();
                                        user.put("email",email.getText().toString().trim());
                                        user.put("name",name.getText().toString().trim());
                                        user.put("password",pass_word.getText().toString().trim());
                                        user.put("phonenumber",phone_number.getText().toString().trim());
                                        user.put("sex",sex_value);
                                        user.put("age",age_value);
                                        dt.collection("Users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d(TAG,"성공");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG,"실패",e);
                                                    }
                                                });
                                        Toast.makeText(SignupActivity.this, "회원가입 되었습니다. 로그인 하세요.", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(i);
                                        finish();

                                        return;
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "회원가입 실패;", task.getException());
                                        Toast.makeText(SignupActivity.this, "회원가입에 실패했습니다.",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }


                            });


                } else {
                   Toast.makeText(SignupActivity.this,"비밀번호 확인이 다릅니다.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}