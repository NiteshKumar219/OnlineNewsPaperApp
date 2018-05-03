package com.mgr.cse.olnp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class UploaderActivity extends AppCompatActivity {

    EditText titleText,deptText,contentText;
    static String authorname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);
        Intent nameIntent = getIntent();
        authorname = nameIntent.getStringExtra("name");
        titleText = (EditText)findViewById(R.id.eventTitleText);
        deptText = (EditText)findViewById(R.id.eventDeptText);
        contentText = (EditText)findViewById(R.id.contentText);
    }

    public void uploadData(View v){
        String title,dept,content;
        title = titleText.getText().toString();
        dept = deptText.getText().toString();
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
                String jsonData = g.toJson(map);
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("news", jsonData);
                client.post("http://www.agroups.in/checking2/college/mgruniv/olnp/uploadNews.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int status, Header[] xr, JSONObject obj) {
                        try {
                            String result = obj.getString("result");
                            boolean err = obj.getBoolean("error");
                            if(err)
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                            else{
                                int id = obj.getInt("id");
                                Intent it = new Intent(UploaderActivity.this,ImageUplActivity.class);
                                it.putExtra("sno",id);
                                startActivity(it);
                            }
                        }
                        catch (Exception e) {
                            Log.e("Error in Uploading data",e+"");
                            Toast.makeText(getApplicationContext(),"Not Uploaded",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, java.lang.Throwable throwable) {
                        Log.e("Response Error", statusCode + "\t" + responseString);
                        Toast.makeText(getApplicationContext(), "Unable to upload Data. Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
                clearData();
            }
        }
    }
    public void clearData(){
        titleText.setText("");
        deptText.setText("");
        contentText.setText("");
    }
}
