package com.nani.pillter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

class BaseActivity extends AppCompatActivity {
    Context context=this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void toastShow(String txt) {
        Toast.makeText(this.getApplicationContext(),txt,Toast.LENGTH_SHORT).show();

    }

    public void newStartActivity(Class activity){
        Intent i = new Intent(getApplicationContext(),activity);
        startActivity(i);
    }
}
