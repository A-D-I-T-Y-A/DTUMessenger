package com.decode.dtumessenger;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.decode.dtumessenger.Models.Message;

import java.util.ArrayList;

public class ChatScreenActivity extends AppCompatActivity {

    RecyclerView MsgRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Name");

        ArrayList<Message> MsgList = new ArrayList<Message>();
        MsgList.add(new Message(1,2,3,"aanya","1234"));
        MsgList.add(new Message(1,2,3,"anjndaijiajoxidk","1234"));

        MsgRecyclerViewAdapter msgRecyclerViewAdapter = new MsgRecyclerViewAdapter(MsgList);
        MsgRecyclerView = (RecyclerView) findViewById(R.id.rv_msg_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        MsgRecyclerView.setLayoutManager(layoutManager);
        MsgRecyclerView.setAdapter(msgRecyclerViewAdapter);
        msgRecyclerViewAdapter.notifyDataSetChanged();

    }

    public class MsgRecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView msg;
        public ImageView image;

        public MsgRecyclerViewHolder(View itemView) {
            super(itemView);
            msg = (TextView) itemView.findViewById(R.id.tv_msg);
            image = (ImageView) itemView.findViewById(R.id.iv_image);
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
            //holder.msg.setText(thisMsg.getContent());
            //holder.image.setImageResource(thisMsg.);



        }
    }
}
