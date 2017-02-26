package com.decode.dtumessenger;

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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity {

    RecyclerView ContactRecyclerView;
    ArrayList<Contact> ContactList;
    ContactRecyclerViewAdapter contactRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        ContactList = new ArrayList<Contact>();
        ContactList.add(new Contact("aanya", "1234"));
        ContactList.add(new Contact("anjndaijiajoxidk", "1234"));

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
            // holder.image.setImageResource();

        }
    }
}
