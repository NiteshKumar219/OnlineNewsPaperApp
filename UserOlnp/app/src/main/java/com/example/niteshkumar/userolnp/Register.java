package com.example.niteshkumar.userolnp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {


    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }



    public void ViewList(View v){
        i = new Intent(Register.this,ListViewA.class);
        startActivity(i);
    }

}
