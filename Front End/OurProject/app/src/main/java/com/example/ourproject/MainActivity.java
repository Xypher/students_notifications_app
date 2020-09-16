package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button sign_up;
    private Button Log_in;
    private EditText usernamefield, passwordfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Sign up button


        usernamefield = (EditText) findViewById(R.id.T_email);
        passwordfield = (EditText) findViewById(R.id.T_Password);

        sign_up = (Button) findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();

            }
        });


        // Log in button
        Log_in = (Button) findViewById(R.id.Log_in);
        Log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = usernamefield.getText().toString();
                String password = passwordfield.getText().toString();

                User user = new User(username, password);

                if (!validatePassword() || !validateUsername())
                    return;


                boolean is_valid = HttpApi.login(user, MainActivity.this);
                String error = "Invalid user name or password";
                if (is_valid) {
                    open_choose();
                } else {
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // user name validate
    private boolean validateUsername() {
        String usernameInput = usernamefield.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            usernamefield.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 10) {
            usernamefield.setError("Username too long");
            return false;
        } else {
            usernamefield.setError(null);
            return true;
        }
    }

    // user password
    private boolean validatePassword() {
        String passwordInput = passwordfield.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordfield.setError("Field can't be empty");
            return false;
        } else if (passwordInput.length() > 10) {
            passwordfield.setError("Username too long");
            return false;
        } else {
            passwordfield.setError(null);
            return true;
        }
    }

    // log in
    public void open_choose() {
        Intent intent = new Intent(this, Choose.class);
        startActivity(intent);
    }

    // sign up
    public void openSignUp() {
        Intent intent = new Intent(this, Signup.class);

        startActivity(intent);
    }
}