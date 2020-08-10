package com.nani.pillter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener,View.OnLongClickListener {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    @BindView(R.id.loginlayout)
    RelativeLayout loginlayout;


    @BindView(R.id.find_idpw) Button find_idpw;
    @BindView(R.id.login) Button login;
    @BindView(R.id.join) Button join;

    EditText id;
    EditText pw;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        ButterKnife.bind(this);
        connectOnClickListner();
        login.setOnLongClickListener(this);
        find_idpw.setOnLongClickListener(this);
        join.setOnLongClickListener(this);
        id = findViewById(R.id.id);
        pw = findViewById(R.id.password);

    }


    public List<View> getAllButtons(ViewGroup layout){
        List<View> viewList = new ArrayList<>();
        for(int i =0; i< layout.getChildCount(); i++){
            View v =layout.getChildAt(i); //해당 테이블로우 전부 가져오기
            viewList.addAll(v.getTouchables()); //해당 테이블로우의 버튼 id 전부 가져오기
        }
        return viewList;
    }
    private void connectOnClickListner() {
        Log.d("test","========================= connectOnClickListener =======================");

        List<View> btnList = getAllButtons(loginlayout);
        for (View btn:btnList) {
            btn.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:
                if(id.getText().toString().equals("")||pw.getText().toString().equals("")){
                    toastShow("id와 pw를 입력하세요.");
                }else{
                    mAuth.signInWithEmailAndPassword(id.getText().toString().trim(),pw.getText().toString().trim())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG,"성공");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        toastShow("로그인 되었습니다.");
                                        newStartActivity(MainActivity.class);
                                    }else{
                                        Log.w(TAG,"로그인 실패",task.getException());
                                        toastShow("회원이 아닙니다.");
                                    }
                                }
                            });
                }

                //toastShow("로그인 되었습니다.");
                //newStartActivity(MainActivity.class);
                break;
            case R.id.find_idpw:
                toastShow("이메일 주소를 통해 계정을 찾으세요.");
                newStartActivity(Find_Account_Activity.class);
                break;
            case R.id.join:
                toastShow("회원가입을 진행하세요.");
                newStartActivity(SignupActivity.class);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch(v.getId()){
            case R.id.login:
                toastShow("로그인 되었습니다.");
                newStartActivity(MainActivity.class);
                break;
            case R.id.find_idpw:
                toastShow("이메일 주소를 통해 계정을 찾으세요.");
                newStartActivity(Find_Account_Activity.class);
                break;
            case R.id.join:
                toastShow("회원가입을 진행하세요.");
                newStartActivity(SignupActivity.class);
                break;
        }
        return true;
    }
}