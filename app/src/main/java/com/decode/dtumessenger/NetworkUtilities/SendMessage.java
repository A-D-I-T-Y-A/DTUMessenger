package com.decode.dtumessenger.NetworkUtilities;

import com.decode.dtumessenger.Models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Aditya on 2/26/2017.
 */

public class SendMessage {

    public SendMessage(){

    }

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    StringBuilder stringBuilder;
    BufferedReader bufferedReader;
    String line;
    String jsonString;
    JSONObject jsonObj;
    HttpURLConnection con = null;
    BufferedReader reader = null;
    JSONArray jsonArray;
    StringBuffer buffer;


    // function get json from url
    // by making HTTP POST or GET mehtod
    public String makeHttpRequest(Message msg) {
        try {
            URL url = new URL("http://192.168.137.1/sendMessage?"+msg.getUrlParams());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            /*con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestProperty("Content-Length",Integer.toString(msg.getUrlParams().getBytes().length));*/
            con.connect();
            InputStream stream = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con != null) {
                con.disconnect();
            }
            try {
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(buffer != null) {
            return buffer.toString();
        }
        else {
            return "F";
        }
    }
}
