package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class add_notification extends AppCompatActivity {
    private Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        create = (Button) findViewById(R.id.create_note);



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNote();
            }
        });

    }

    public void openNote() {
        Intent intent = new Intent(this, Notification.class);
        startActivity(intent);
    }
}