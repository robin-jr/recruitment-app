package com.jrnspark.recruitmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText name, userName, email, password;
    TextView signin;
    Button register;
    String sUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        userName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        
        register = findViewById(R.id.register);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        register.setOnClickListener((view)->{
            if (userName.getText().toString().length() == 0 || password.getText().toString().length() == 0 || email.getText().toString().length() == 0 || name.getText().toString().length() == 0) {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("Name",name.getText().toString());
            map.put("Email",email.getText().toString());
            map.put("Password",password.getText().toString());

            userRef.child(userName.getText().toString()).updateChildren(map);

            sUserName = userName.getText().toString();

            name.setText("");
            userName.setText("");
            email.setText("");
            password.setText("");

            Toast.makeText(this,"User Registered Successfully",Toast.LENGTH_SHORT).show();

            login();
        });
        signin.setOnClickListener((view)->{
            Intent intent = new Intent(SignUp.this,LoginActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        Intent intent = new Intent(SignUp.this, ListBasic.class);
        intent.putExtra("userName",sUserName);
        startActivity(intent);
    }
}