package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

public class Notification extends AppCompatActivity {
    private com.google.android.material.floatingactionbutton.FloatingActionButton add_not;
    private String class_name, notifs_titiles[];
    private User user;
    private ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mylist = findViewById(R.id.mylist);
        LocalUserDetails details = new LocalUserDetails(this);
        user = details.getUser();
        class_name = getIntent().getStringExtra("name");

        JSONArray notifs = HttpApi.getNotificationsByClass(user, this, class_name);

        notifs_titiles = new String[notifs.length()];
        for(int i = 0; i  < notifs.length(); ++i){

            try {
                notifs_titiles[i] = notifs.getJSONObject(i).getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                Notification.this,
                android.R.layout.simple_list_item_1,
                notifs_titiles);

        mylist.setAdapter(myAdapter);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String name = notifs_titiles[position];
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Notification.this, Notification.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                });
            }
        });



        add_not  = findViewById(R.id.add_not);

        add_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddnot();
            }
        });

    }
    public void openAddnot(){
        Intent intent = new Intent(this,add_notification.class);
        startActivity(intent);
    }
}