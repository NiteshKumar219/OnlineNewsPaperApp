package com.mgr.cse.olnp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdmiCont extends AppCompatActivity {

    Intent i;
    static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admi_cont);
        Intent i = getIntent();
        name = i.getStringExtra("name");

    }

    public void Uplodenews(View v){
        i = new Intent(AdmiCont.this,UploaderActivity.class);
        i.putExtra("name",name);
        startActivity(i);
    }

    public void ViewList(View v){
        i = new Intent(AdmiCont.this,ListViewA.class);
        startActivity(i);
    }


}