package com.mgr.cse.olnp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Arutselvan on 24-Oct-17.
 */

public class InternetChecker {

    public static boolean checkInternet(Context ctx,ConnectivityManager cm){
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if(!(activeNetworkInfo != null && activeNetworkInfo.isConnected())){
            Log.e("Internet", "No Internet");
            Toast.makeText(ctx,"Please connect the Internet", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
