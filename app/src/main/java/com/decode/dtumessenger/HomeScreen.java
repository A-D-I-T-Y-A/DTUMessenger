package com.decode.dtumessenger;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.decode.dtumessenger.Models.Contact;
import com.decode.dtumessenger.Models.Message;

import java.security.acl.Group;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity {

    RecyclerView ContactRecyclerView;
    ArrayList<Contact> ContactList;
    ContactRecyclerViewAdapter contactRecyclerViewAdapter;
    FloatingActionButton fabAdd;
    EditText adEtName, adEtStatus, adEtId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);




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
                        intent.putExtra("CHAT_ID", 1);
                        startActivity(intent);
                    }
                });
            }
            else {
                holder.contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeScreen.this, ChatScreenActivity.class);
                        intent.putExtra("CHAT_ID", 2);
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

        editDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        editDialog.setNegativeButton("Cancel", null);
        editDialog.create().show();
    }
}
