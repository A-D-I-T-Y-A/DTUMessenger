package com.decode.dtumessenger;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.decode.dtumessenger.Models.Contact;
import com.decode.dtumessenger.Models.Message;
import com.decode.dtumessenger.NetworkUtilities.InsertUser;
import com.decode.dtumessenger.NetworkUtilities.SendMessage;
import com.decode.dtumessenger.NetworkUtilities.UpdateUser;

import java.security.acl.Group;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity {

    RecyclerView ContactRecyclerView;
    ArrayList<Contact> ContactList;
    ContactRecyclerViewAdapter contactRecyclerViewAdapter;
    FloatingActionButton fabAdd;
    EditText adEtName, adEtStatus, adEtId, adEtNum;

    String name,contact,status,id;

    SharedPreferences pref;

    int my_id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        pref = getSharedPreferences("MyDetails",MODE_PRIVATE);
        my_id = pref.getInt("USER_ID",-1);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });


        ContactList = new ArrayList<Contact>();
        ContactList.add(new Contact("John", "1234"));
        ContactList.add(new Contact("Rob", "1234"));

        contactRecyclerViewAdapter = new ContactRecyclerViewAdapter(ContactList);
        ContactRecyclerView = (RecyclerView) findViewById(R.id.rv_chat_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ContactRecyclerView.setLayoutManager(layoutManager);
        ContactRecyclerView.setAdapter(contactRecyclerViewAdapter);
        contactRecyclerViewAdapter.notifyDataSetChanged();
    }

    public class ContactRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView contact;
        public CircleImageView image;

        public ContactRecyclerViewHolder(View itemView) {
            super(itemView);
            contact = (TextView) itemView.findViewById(R.id.tv_contact_name);
            image = (CircleImageView) itemView.findViewById(R.id.iv_image_contact);
        }
    }

    public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewHolder> {

        ArrayList<Contact> mContact;

        public ContactRecyclerViewAdapter(ArrayList<Contact> mContact) {
            this.mContact = mContact;
        }

        @Override
        public int getItemCount() {
            return mContact.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public HomeScreen.ContactRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = getLayoutInflater();
            View itemView;
            itemView = li.inflate(R.layout.list_contact_item, null);
            ContactRecyclerViewHolder contactRecyclerViewHolder = new ContactRecyclerViewHolder(itemView);
            return contactRecyclerViewHolder;
        }

        @Override
        public void onBindViewHolder(ContactRecyclerViewHolder holder, int position) {

            Contact thisContact = mContact.get(position);
            holder.contact.setText(thisContact.getName());
            if(thisContact.getName().equals("John")) {
                holder.contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeScreen.this, ChatScreenActivity.class);
                        intent.putExtra("CHAT_ID", 20);
                        startActivity(intent);
                    }
                });
            }
            else {
                holder.contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeScreen.this, ChatScreenActivity.class);
                        intent.putExtra("CHAT_ID", 30);
                        startActivity(intent);
                    }
                });
            }
            // holder.image.setImageResource();

        }
    }


    public void openAddDialog() {

        LayoutInflater li = LayoutInflater.from(this);
        View editDialogView = li.inflate(R.layout.dialog_add_detail, null);
        final AlertDialog.Builder editDialog = new AlertDialog.Builder(this);
        editDialog.setView(editDialogView);

        adEtName = (EditText) editDialogView.findViewById(R.id.ad_et_name);
        adEtStatus = (EditText) editDialogView.findViewById(R.id.ad_et_status);
        adEtId = (EditText) editDialogView.findViewById(R.id.ad_et_id);
        adEtNum = (EditText) editDialogView.findViewById(R.id.ad_et_num);


        if(my_id != -1){
            adEtId.setEnabled(false);
            adEtNum.setEnabled(false);
            adEtId.setText(Integer.toString(my_id));
            adEtName.setText(pref.getString("NAME","null"));
            adEtStatus.setText(pref.getString("STATUS","Hi There"));
            adEtNum.setText(pref.getString("CONTACT", "666"));
            //adEtNum.setVisibility(View.GONE);
        }

        editDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                name = adEtName.getText().toString();
                status = adEtStatus.getText().toString();
                id = adEtId.getText().toString();
                contact = adEtNum.getText().toString();
                //contact = "666";

                if(my_id == -1){
                    new registerUser().execute();
                }
                else{
                    new updateUser().execute();
                }
                dialog.dismiss();

            }
        });

        editDialog.setNegativeButton("Cancel", null);
        editDialog.create().show();
    }

    class registerUser extends AsyncTask<Void, String, String> {


        /**
         * getting All posts from url
         * */
        protected String doInBackground(Void... params) {

            // getting JSON string from URL
            String respone = new InsertUser().makeHttpRequest(name,id,contact,status);
            //Log.d("send",params[0].getUrlParams());
            Log.d("send",respone);

            if(respone.equals("S")){
                my_id = Integer.parseInt(id);
                pref.edit().putInt("USER_ID",my_id).commit();
                pref.edit().putString("NAME",name).commit();
                pref.edit().putString("STATUS",status).commit();
                pref.edit().putString("CONTACT",contact).commit();
                return "done";
            }
            else
            {
                return "failed";
            }
        }
    }

    class updateUser extends AsyncTask<Void, String, String> {


        /**
         * getting All posts from url
         * */
        protected String doInBackground(Void... params) {

            // getting JSON string from URL
            String respone = new UpdateUser().makeHttpRequest(my_id,name,status);
            //Log.d("send",params[0].getUrlParams());
            Log.d("send",respone);

            if(respone.equals("S")){
                pref.edit().putString("NAME",name).commit();
                pref.edit().putString("STATUS",status).commit();
                return "done";
            }
            else
            {
                return "failed";
            }

        }


    }
}
