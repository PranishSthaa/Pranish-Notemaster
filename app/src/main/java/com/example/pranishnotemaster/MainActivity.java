package com.example.pranishnotemaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login, goToRegister;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btn_login);
        goToRegister = findViewById(R.id.btn_go_to_register);
        
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = email.getText().toString();
                String passTxt = password.getText().toString();
                
                DBHelper db = new DBHelper(MainActivity.this);
                boolean loginSuccessful = db.login(emailTxt, passTxt);
                if(loginSuccessful){
                    Intent intent =  new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Incorrect email or password. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}