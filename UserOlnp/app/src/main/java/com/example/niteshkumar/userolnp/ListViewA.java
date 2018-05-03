package com.example.niteshkumar.userolnp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListViewA extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    public static final String GET_IMAGE_URL = "http://www.agroups.in/checking2/college/mgruniv/olnp/showAllImages.php";
    // private static String urll = "http://192.168.43.124/retimg.php";

    private static final String URL_PRODUCTS = "http://www.agroups.in/checking2/college/mgruniv/olnp/getAllImages.php";
    ArrayList<Contact> contactList;
    ///
    public GetAlImages getAlImages;

    public static final String BITMAP_ID = "BITMAP_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        contactList = new ArrayList<>();


        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getURLs();
        getalldata();
    }

    private void getImages() {
        class GetImages extends AsyncTask<Void, Void, Void> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListViewA.this, "Downloading images...", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomListView customList = new CustomListView(ListViewA.this, GetAlImages.imageURLs, GetAlImages.bitmaps, contactList);
                listView.setAdapter(customList);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAlImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListViewA.this, "Loading...", "Please Wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, ViewFullImage.class);
        Log.e("Current User ID",contactList.get(i).sno);
        intent.putExtra("ID",i);
        intent.putExtra("SNO",contactList.get(i).sno);
        //disply to next page full screen all data and image
        intent.putExtra("title",contactList.get(i).title);
        intent.putExtra("content",contactList.get(i).content);
        intent.putExtra("dept",contactList.get(i).dept);
        intent.putExtra("uploadtime",contactList.get(i).uploadtime);
        intent.putExtra("authorname",contactList.get(i).authorname);

        Log.e("title",contactList.get(i).title);
        Log.e("content",contactList.get(i).content);
        Log.e("dept",contactList.get(i).dept);
        Log.e("uploadtime",contactList.get(i).uploadtime);
        Log.e("authorname",contactList.get(i).authorname);
        //
        startActivity(intent);
    }






    private void getalldata() {
        new  GetContacts().execute();

    }

    public class GetContacts extends AsyncTask<Void, Void, Void> {

        private String TAG = ListViewA.class.getSimpleName();

        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListViewA.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(URL_PRODUCTS);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {


                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node


                    JSONArray contacts = jsonObj.getJSONArray("result");
                    Log.e("contactsize", contacts.length() + "");
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        Contact ct = new Contact();

                        ct.sno = c.getString("sno");
                        ct.authorname = c.getString("authorname");
                        ct.title = c.getString("title");
                        ct.dept = c.getString("dept");
                        ct.content = c.getString("content");
                        ct.uploadtime = c.getString("uploadtime");

                        //JSONObject phone = c.getJSONObject("phone");

                        /*HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("sno", id);
                        contact.put("authorname", dname);
                        contact.put("titile", dtitle);
                        contact.put("dept", ddept);
                        contact.put("content", dcontent);
                        contact.put("uploadtime", duploadtime);
                        //contact.put("mobile", mobile);*/

                        // adding contact to contact list
                        contactList.add(ct);//is this line working ?
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();
                /**
                 * Updating parsed JSON data into ListView
                 * */
                //ListAdapter adapter = new SimpleAdapter(
                //      TabLayoutDemo.this, contactList,
                //    R.layout.list_item, new String[]{"id","name", "email"}, new int[]{R.id.id,
                //  R.id.name,R.id.email});

                //lv.setAdapter(adapter);
            }

        }

        public void getTime()
        {
            DateFormat df = new SimpleDateFormat();
            String date = df.format(Calendar.getInstance().getTime());
        }
    }
    class Contact{
        public  String sno,title,authorname,content,dept,uploadtime;

    }
