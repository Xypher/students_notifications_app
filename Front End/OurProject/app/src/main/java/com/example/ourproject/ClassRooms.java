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

public class ClassRooms extends AppCompatActivity {
    private com.google.android.material.floatingactionbutton.FloatingActionButton floatingActionButton;
    private String classes_names[];
    private ListView classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_rooms);
        // arrow back\
        LocalUserDetails details = new LocalUserDetails(ClassRooms.this);
        User user = details.getUser();




        getSupportActionBar().setTitle("back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // arrow back
        floatingActionButton = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddclassRoom();
            }
        });
        //

        if (user.getType().equals("teacher")){
            floatingActionButton.setVisibility(View.GONE);

        }

        classList = findViewById(R.id.class_list);

        JSONArray classrooms =  HttpApi.getClassrooms(user, ClassRooms.this);


        classes_names = new String[classrooms.length()];
        for(int i = 0; i < classrooms.length(); ++i){

            try {
                classes_names[i] = classrooms.getJSONObject(i).getString("class_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                ClassRooms.this,
                android.R.layout.simple_list_item_1,
                classes_names);

        classList.setAdapter(myAdapter);

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String name = classes_names[position];
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ClassRooms.this, Notification.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                });
            }
        });


    }

    public void openAddclassRoom() {
        Intent intent = new Intent(this, addClassRoom.class);
        startActivity(intent);
    }
}