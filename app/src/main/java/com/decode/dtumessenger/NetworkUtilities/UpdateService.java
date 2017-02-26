package com.decode.dtumessenger.NetworkUtilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateService extends Service {
    public UpdateService() {
    }



    private final IBinder mBinder = new MyBinder();
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_PID = "pid";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DATE = "eventdate";
    private static final String TAG_VENUE = "venue";
    private static final String TAG_TIMESTAMP = "ts";
    private static final String TAG_IMGURL = "imgurl";
/*
    ArrayList<PostsItem> plist = new ArrayList<PostsItem>();

    SharedPreferences msgspref;
    int lastid;

    List<String> dep_list = new ArrayList<String>();

    ArrayList<String> NewUpdates = new ArrayList<String>();
    Departments dep = new Departments();

    String curr_dep;

    int notif_count = 0;
    //private ArrayList<String> list = new ArrayList<String>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        msgspref = getSharedPreferences("msgHistory", MODE_PRIVATE);
        deps_latest = getSharedPreferences("dep_LATEST",MODE_PRIVATE);

        new LoadAllposts().execute();

        Log.d("UpdateStarter", "Service run ");

        return Service.START_NOT_STICKY;
    }
*/
    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        UpdateService getService() {
            return UpdateService.this;
        }
    }

    class LoadAllposts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //String this_dep = curr_dep;

        /**
         * getting All posts from url
         * */
        protected String doInBackground(String... params) {
/*
            // getting JSON string from URL
            plist.clear();
            for(int i=0;i<dep_list.size();i++) {
                if (deps_pref.getBoolean(dep_list.get(i), false)) {
                    curr_dep = dep_list.get(i);
                    final JSONArray jsonArray = new JSONParser().makeHttpRequest(curr_dep);
                    JSONObject c = null;
                    if(jsonArray != null) {
                        try {
                            Log.d("UpdateStarter","value returned");
                            c = jsonArray.getJSONObject(0);
                            Log.d("UpdateStarter",c.getString(TAG_CONTENT)+"check");

                            if (c.getInt(TAG_PID) > deps_latest.getInt(curr_dep, 0)) {
                                plist.add(new PostsItem(c.getInt(TAG_PID),c.getString(TAG_TITLE), c.getString(TAG_CONTENT), c.getString(TAG_VENUE),
                                        c.getString(TAG_DATE), c.getString(TAG_TIMESTAMP), null));
                                NewUpdates.add(dep.getDepartmentName(curr_dep));
                                deps_latest.edit().putInt(curr_dep, c.getInt(TAG_PID)).commit();
                                Log.d("UpdateStarter",curr_dep);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
*/            return "done";
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

/*            for(int i=0;i<plist.size();i++){
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

                // Build notification
                // Actions are just fake
                Notification noti = new Notification.Builder(getApplicationContext())
                        .setContentTitle("New update from " + NewUpdates.get(i))
                        .setContentText(plist.get(i).getTitle()).setSmallIcon(R.drawable.srdtu)
                        .setContentIntent(pIntent)
                        .setStyle(new Notification.BigTextStyle().bigText(plist.get(i).getContent())) .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // hide the notification after its selected
                noti.flags |= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(notif_count++, noti);

            }

*/        }

    }
}
