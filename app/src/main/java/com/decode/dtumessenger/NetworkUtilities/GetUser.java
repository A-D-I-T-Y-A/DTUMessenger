package com.decode.dtumessenger.NetworkUtilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Aditya on 2/27/2017.
 */

public class GetUser {

    static InputStream is = null;
    static JSONObject jObj = null;
    StringBuilder stringBuilder;
    BufferedReader bufferedReader;
    String line;
    String jsonString;
    JSONObject jsonObj;
    HttpURLConnection con = null;
    BufferedReader reader = null;
    JSONArray json;
    StringBuffer buffer;


    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(int user_id) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("user_id", Integer.toString(user_id))
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.137.1/getUser.php")
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("send",request.toString());
            if(response.isSuccessful()){
                Log.d("send","success");
                line = response.body().string();
                //Log.d("send",response.body().string());
                jsonObj = new JSONObject(line);
                Log.d("send",json.toString());
                return jsonObj;
            }

            // Do something with the response.
        } catch (IOException e) {
            Log.d("send",e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
