package com.mgr.cse.olnp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    EditText nameText,pwdText;
    TextView forgetText;
    CheckBox rememberCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameText = (EditText)findViewById(R.id.userNameText);
        pwdText = (EditText)findViewById(R.id.userPassText);
        forgetText = (TextView)findViewById(R.id.forgetText);
        rememberCheck = (CheckBox)findViewById(R.id.rememberCheck);

        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void processLogin(View v){
        String name,pwd;
        name = nameText.getText().toString();
        pwd = pwdText.getText().toString();
        if(name.equals("")||pwd.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all the Credentials",Toast.LENGTH_LONG).show();
        }
        else {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            if (InternetChecker.checkInternet(getApplicationContext(), connectivityManager)) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("name",name);
                params.add("pwd",pwd);
                client.post("http://www.agroups.in/checking2/college/mgruniv/olnp/checkUserData.php",params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int status, Header[] xr, JSONObject obj) {
                        try {
                            if(!obj.has("err")){
                                String role = obj.getString("role");
                                Toast.makeText(getApplicationContext(),"Your role is "+role,Toast.LENGTH_LONG).show();
                                if(rememberCheck.isChecked()){
                                    //No need for future login --code
                                }
                                if(role.equals("staff")){
                                    Intent i = new Intent(LoginActivity.this,AdmiCont.class);
                                    i.putExtra("name",nameText.getText().toString());
                                    startActivity(i);
                                }
                                /*
                                else{
                                    Intent i = new Intent(LoginActivity.this,UploaderActivity.class);
                                    i.putExtra("name",nameText.getText().toString());
                                    startActivity(i);
                                    finish();
                                }*/
                            }
                            else{
                                String err = obj.getString("err");
                                Toast.makeText(getApplicationContext(),"Invalid Credential: "+err,Toast.LENGTH_LONG).show();
                            }
                        }
                        catch(Exception e){
                            Log.e("Parse Error",e+"");
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, java.lang.Throwable throwable) {
                        Log.e("Response Error", statusCode + "\t" + responseString);
                        Toast.makeText(getApplicationContext(), "Unable to verify Data. Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
