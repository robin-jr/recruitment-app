package com.jrnspark.recruitmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrnspark.recruitmentapp.utils.CurrentUser;
import com.jrnspark.recruitmentapp.utils.Tools;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private View parent_view;
    private Button signinBtn;
    private TextView signUp;
    private EditText userName, password;

    public  String sUserName , sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        parent_view = findViewById(android.R.id.content);
        signinBtn = findViewById(R.id.singninbtn);
        signUp = findViewById(R.id.signup);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);


        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        if (sUserName != null) {
            login();
        }

        signinBtn.setOnClickListener((view) -> {
            if (userName.getText().toString().length() == 0 || password.getText().toString().length() == 0) {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            userRef.child(userName.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        if (snapshot.child("Password").getValue().toString().equals(password.getText().toString())) {
                            sUserName = userName.getText().toString();

                            login();
                        }
                        makeToast("Incorrect Password");

                    } else
                        makeToast("User not registered");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        });

        signUp.setOnClickListener((view) -> {
            Intent intent = new Intent(LoginActivity.this, SignUp.class);
            startActivity(intent);
        });

    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void login() {
        userName.setText("");
        password.setText("");
        makeToast("Login Successful");
        try (PrintWriter out = new PrintWriter("filename.txt")) {
            out.println(sUserName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(LoginActivity.this, ListBasic.class);
        intent.putExtra("userName",sUserName);
        startActivity(intent);
    }
}