package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;


public class addClassRoom extends AppCompatActivity {
    private LocalUserDetails user;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_room);

        User USER = user.getUser();

        name = (EditText) findViewById(R.id.class_name);
        String classname = name.getText().toString();
        HttpApi.createClassRoom(USER, addClassRoom.this ,classname );
    }
}