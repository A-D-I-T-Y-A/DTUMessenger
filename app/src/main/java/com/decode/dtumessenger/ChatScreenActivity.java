package com.decode.dtumessenger;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChatScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Name");
    }
}
