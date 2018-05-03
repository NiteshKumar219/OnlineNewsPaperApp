package com.example.niteshkumar.userolnp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewFullImage extends AppCompatActivity {


    static String curUserId;
    ImageView imageView;
    PhotoViewAttacher mAttacher;
    TextView title, content, dept, uploadtime, authorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_image);


    Intent intent = getIntent();
    int i = intent.getIntExtra("ID",0);
    curUserId = intent.getStringExtra("SNO");
    String tl = intent.getStringExtra("title");
    String ct = intent.getStringExtra("content");
    String dt = intent.getStringExtra("dept");
    String ut = intent.getStringExtra("uploadtime");
    String an = intent.getStringExtra("authorname");

    imageView = (ImageView) findViewById(R.id.imageViewFull);
        imageView.setImageBitmap(GetAlImages.bitmaps[i]);
        mAttacher= new PhotoViewAttacher(imageView);
        mAttacher.update();

        Log.e("again title", tl);
        Log.e("again content", ct);
        Log.e("again dept", dt);
        Log.e("again uploadtime", ut);
        Log.e("again authorname", an);

    title = (TextView) findViewById(R.id.title);
    content = (TextView) findViewById(R.id.content);
    dept = (TextView) findViewById(R.id.dept);
    uploadtime = (TextView) findViewById(R.id.uploadtime);
    authorname = (TextView) findViewById(R.id.authorname);

        title.setText(tl);
        content.setText(ct);
        dept.setText(dt);
        uploadtime.setText(ut);
        authorname.setText(an);
}
}
