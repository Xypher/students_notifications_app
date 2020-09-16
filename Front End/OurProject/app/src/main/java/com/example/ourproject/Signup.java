package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.BitSet;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout full_name;
    private TextInputLayout Id;
    private TextInputLayout Choose_T;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        // to register

        textInputEmail = findViewById(R.id.text_input_email);

        textInputUsername = findViewById(R.id.text_input_username);

        textInputPassword = findViewById(R.id.text_input_password);

        confirm = (Button) findViewById(R.id.confirm);

        full_name = findViewById(R.id.full_name);
        Id = findViewById(R.id.Id);
        Choose_T = findViewById(R.id.Choose_T);
        final String email = textInputEmail.getEditText().getText().toString();
        final String username = textInputUsername.getEditText().getText().toString();
        final String password = textInputPassword.getEditText().getText().toString();
        final String id = Id.getEditText().getText().toString();
        final String fullname = full_name.getEditText().getText().toString();
        final String Type = Choose_T.getEditText().getText().toString().toLowerCase();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(username, fullname, id, email, password, Type);
                JResponse<JSONArray, JSONObject> res = HttpApi.Register(user, Signup.this);

                //  res.errors.getJSONObject(idx).getString("message");
                if (res.success) {
                    openLogin();
                } else {
                    String s = "";
                    for (int i = 0; i < res.errors.length(); ++i) {
                        try {
                            s += res.errors.getJSONObject(i).getString("message");
                            s += '\n';
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Toast.makeText(Signup.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // confirm button
    public void openLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    // validation fields
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 10) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        }

        String input = "Email: " + textInputEmail.getEditText().getText().toString();

        input += "\n";
        input += "Username" + textInputUsername.getEditText().getText().toString();
        input += "\n";
        input += "Password" + textInputPassword.getEditText().getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

    }
    // validation fields
}