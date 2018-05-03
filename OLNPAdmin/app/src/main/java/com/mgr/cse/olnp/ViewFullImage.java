package com.mgr.cse.olnp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
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

    public void publishData(View v) {
       /* String title,content,dept,uploadtime,authorname;
        title = title.().toString();
        dept = dept.getText().toString();
        content = contentText.getText().toString();
        if(title.equals("")||dept.equals("")||content.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all the Data",Toast.LENGTH_LONG).show();
        }
        else{
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            if (InternetChecker.checkInternet(getApplicationContext(), connectivityManager)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name",authorname);
                map.put("title", title);
                map.put("dept", dept);
                map.put("content", content);
                Gson g = new Gson();
                String jsonData = g.toJson(map);*/

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("curUserId", curUserId);
        client.post("http://www.agroups.in/checking2/college/mgruniv/olnp/updateNewsStatus.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int status, Header[] xr, JSONObject obj) {
                try {
                    Log.e("Publish Status","success"+curUserId);

                    String result = obj.getString("result");
                    boolean err = obj.getBoolean("error");
                    if (err)
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    else {

                       Toast.makeText(getApplicationContext(),"Data Publish Success full",Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    Log.e("Error in Uploading data", e + "");
                    Toast.makeText(getApplicationContext(), "Not Uploaded", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, java.lang.Throwable throwable) {
                Log.e("Response Error", statusCode + "\t" + responseString);
                Toast.makeText(getApplicationContext(), "Unable to upload Data. Please Try Again", Toast.LENGTH_LONG).show();
            }
        });
        //clearData();
    }


    /*
//Tutorial how to zoom photo on ImageView in Android Studio
    final ImageView zoom = (ImageView) findViewById(R.id.imageViewFull);
    final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
    zoom.startAnimation(zoomAnimation);
////Tutorial how to zoom photo on ImageView in Android Studio
    ////https://android-coffee.com/tutorial-how-to-zoom-photo-on-imageview-in-android-studio-1-5-1/
    */
   // public void clearData() {
       // title.setText("");
      //  dept.setText("");
      //  content.setText("");
    }



