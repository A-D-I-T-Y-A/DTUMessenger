package com.decode.dtumessenger.NetworkUtilities;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Aditya on 2/27/2017.
 */

public class UpdateUser {

    String resp = "error";
    // function get json from url
    // by making HTTP POST or GET mehtod
    public String makeHttpRequest(int id, String name, String status) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("user_id", Integer.toString(id))
                .add("name", name)
                .add("status", status)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.137.1/updateUser.php")
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("send",request.toString());
            if(response.isSuccessful()){
                Log.d("send","success");
                return "S";
            }

            // Do something with the response.
        } catch (IOException e) {
            Log.d("send",e.toString());
            e.printStackTrace();
        }

        return "F";
    }

}
