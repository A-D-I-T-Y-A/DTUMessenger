package com.decode.dtumessenger;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.decode.dtumessenger.Models.Message;
import com.decode.dtumessenger.NetworkUtilities.GetMessages;
import com.decode.dtumessenger.NetworkUtilities.SendMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatScreenActivity extends AppCompatActivity {

    RecyclerView MsgRecyclerView;
    CircleImageView actionBarLogo;
    ImageButton sendBtn;
    EditText eMsg;
    ArrayList<Message> MsgList;
    MsgRecyclerViewAdapter msgRecyclerViewAdapter;
    // Progress Dialog
    private ProgressDialog pDialog;
    int latestmsg = -1;
    int chat_id = 2;
    int my_id = 1;

    SharedPreferences spref;

    // Creating JSON Parser object
    SendMessage jParser = new SendMessage();
    String msg;

    // JSON Node names
    private static final String MSG_ID = "msg_id";
    private static final String CHAT_ID = "chat_id";
    private static final String USER_ID = "user_id";
    private static final String CONTENT = "content";
    private static final String TIME = "time";

    // posts JSONArray
    JSONArray msgs = null;

    Handler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Name");
        actionBarLogo = (CircleImageView) findViewById(R.id.action_bar_logo);
        sendBtn = (ImageButton)findViewById(R.id.btn_send);
        eMsg = (EditText)findViewById(R.id.chat_box);

        chat_id = getIntent().getIntExtra("CHAT_ID",2);

        spref = getSharedPreferences("LatestMessage",MODE_PRIVATE);
        //latestmsg = spref.getInt(Integer.toString(chat_id),-1);

        actionBarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatScreenActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = eMsg.getText().toString();
                if(!msg.equals("") && !msg.isEmpty() && msg!=null){
                    Message newmsg = new Message(1,2,3,msg,"1234");
                    new PostMessage().execute(newmsg);
                }
            }
        });

        MsgList = new ArrayList<Message>();
        MsgList.add(new Message(1,2,3,"aanya","1234"));
        MsgList.add(new Message(1,2,3,"anjndaijiajoxidk","1234"));
        MsgList.add(new Message(1,2,3,"tester 3","1234"));

        msgRecyclerViewAdapter = new MsgRecyclerViewAdapter(MsgList);
        MsgRecyclerView = (RecyclerView) findViewById(R.id.rv_msg_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        MsgRecyclerView.setLayoutManager(layoutManager);
        MsgRecyclerView.setAdapter(msgRecyclerViewAdapter);
        msgRecyclerViewAdapter.notifyDataSetChanged();

        new ReceiveMessages().execute();

        //update check regular
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new ReceiveMessages().execute();
                handler.postDelayed(this, 2000);
            }
        }, 2000);

    }

    public class MsgRecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView msg;
        public CircleImageView image;

        public MsgRecyclerViewHolder(View itemView) {
            super(itemView);
            msg = (TextView) itemView.findViewById(R.id.tv_msg);
            image = (CircleImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    public class MsgRecyclerViewAdapter extends RecyclerView.Adapter<MsgRecyclerViewHolder>{

        ArrayList<Message> mMsg;

        public MsgRecyclerViewAdapter(ArrayList<Message> mMsg) {
            this.mMsg = mMsg;
        }

        @Override
        public int getItemCount() {
            return mMsg.size();
        }

        @Override
        public int getItemViewType(int position) {
            // this will be done using flag as to whose msg is coming.. for now right left it is
            return position%2;
        }

        @Override
        public MsgRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = getLayoutInflater();
            View itemView;
            if(viewType==0){
                itemView = li.inflate(R.layout.list_item, null);
            }else{
                itemView = li.inflate(R.layout.list_item_next, null);
            }
            MsgRecyclerViewHolder msgRecyclerViewHolder = new MsgRecyclerViewHolder(itemView);
            return msgRecyclerViewHolder;
        }

        @Override
        public void onBindViewHolder(MsgRecyclerViewHolder holder, int position) {

            Message thisMsg = mMsg.get(position);
            holder.msg.setText(thisMsg.getContent());
            //holder.image.setImageResource(thisMsg.);

        }
    }


    //Network Thread
    class PostMessage extends AsyncTask<Message, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChatScreenActivity.this);
            pDialog.setMessage("Sending. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All posts from url
         * */
        protected String doInBackground(Message... params) {

            // getting JSON string from URL
            String respone = new SendMessage().makeHttpRequest(params[0],getApplicationContext());
            Log.d("send",params[0].getUrlParams());
            Log.d("send",respone);

            if(respone.equals("S")){
                MsgList.add(params[0]);
                return "done";
            }
            else
            {
                return "failed";
            }

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(final String result) {
            // dismiss the dialog after msg sent

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    if(result.equals("failed")){
                        Toast.makeText(ChatScreenActivity.this,"Message failed to send",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        msgRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    pDialog.dismiss();

                }
            });

        }

    }

    //MessageReciever
    class ReceiveMessages extends AsyncTask<Void,String,String>{

        @Override
        protected String doInBackground(Void... voids) {
            // getting JSON string from URL
            final JSONArray jsonArray = new GetMessages().makeHttpRequest(2,latestmsg);
            Log.d("get",Integer.toString(latestmsg));



            // Check your log cat for JSON reponse
            //Log.d("All posts: ", json.toString());

            try {
                // looping through All posts
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    // Storing each json item in variable
                    MsgList.add(new Message(c.getInt(MSG_ID),c.getInt(CHAT_ID),c.getInt(USER_ID),c.getString(CONTENT),
                            c.getString(TIME)));
                }

                latestmsg = MsgList.get(MsgList.size()-1).getMsg_id();
                //} else {
                //Toast.makeText(getApplicationContext(),"Network Error Occured",Toast.LENGTH_LONG).show();
                //}
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(jsonArray.length()>0)
                return "done";
            else
                return "nothing";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("done")){
                msgRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
