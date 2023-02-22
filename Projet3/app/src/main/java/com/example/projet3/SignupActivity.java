package com.example.projet3;

import static com.example.projet3.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText username ,email ,pass ;
    TextView login;
    Button signUp;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);
        setContentView(layout.activity_signup);
        username=findViewById(id.signup_username);
        email=findViewById(id.signup_email);
        pass=findViewById(id.signup_password);
        signUp = findViewById(id.signup_Button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = username.getText().toString();
                String email1 = email.getText().toString();
                String pass1 = pass.getText().toString();
                boolean b =databaseHelper.insertUserData(name1,email1,pass1);
                if (b){
                    Toast.makeText(SignupActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(SignupActivity.this,"Failed To insert Data",Toast.LENGTH_SHORT).show();
                }
            }
        });

        login=findViewById(id.loginRedirectText);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }
}