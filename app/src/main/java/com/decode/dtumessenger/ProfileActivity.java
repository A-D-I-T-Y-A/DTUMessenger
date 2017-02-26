package com.decode.dtumessenger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.decode.dtumessenger.Models.Message;
import com.decode.dtumessenger.NetworkUtilities.GetMessages;
import com.decode.dtumessenger.NetworkUtilities.GetUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    ImageView ProfilePic;
    TextView tstatus;
    TextView tcontact;
    int user_id;
    String status;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        user_id = getIntent().getIntExtra("USER_ID",1);
        ProfilePic = (ImageView)findViewById(R.id.iv_profile_pic);
        tcontact = (TextView)findViewById(R.id.tv_contact_item);
        tstatus = (TextView)findViewById(R.id.tv_status);



        new LoadImage().execute(Integer.toString(user_id));
    }

    public class LoadImage extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String surl = "http://192.168.137.1/"+strings[0]+".jpg";
            final Bitmap bmp = getBitmapFromURL(surl);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(bmp!=null){
                        ProfilePic.setImageBitmap(bmp);
                    }
                }
            });
            return null;
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    class ReceiveMessages extends AsyncTask<Void,String,String>{

        @Override
        protected String doInBackground(Void... voids) {
            // getting JSON string from URL
            final JSONObject jsonObject = new GetUser().makeHttpRequest(user_id);
            //Log.d("get",Integer.toString(latestmsg));

            if(jsonObject != null) {
                try {
                    status = jsonObject.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    contact = jsonObject.getString("contact");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "done";
            }
            else
                return "nothing";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("done")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tcontact.setText(contact);
                        tstatus.setText(status);
                    }
                });
            }
        }
    }

}
