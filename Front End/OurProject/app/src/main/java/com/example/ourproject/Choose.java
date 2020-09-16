package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose extends AppCompatActivity {
    private Button class_room_button;
    private Button calender_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        // arrow back
        getSupportActionBar().setTitle("back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // arrow back
        // class button
        class_room_button = (Button) findViewById(R.id.class_room_button);
        class_room_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClassRooms();
            }
        });

        // calender button
        calender_button = (Button) findViewById(R.id.calender_button);
        calender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalender();
            }
        });
    }
    // class room button
    public void openClassRooms(){
        Intent intent = new Intent(this,ClassRooms.class);
        startActivity(intent);
    }

    // calender button
    public void openCalender(){
        Intent intent = new Intent(this,Calender.class);
        startActivity(intent);
    }
}