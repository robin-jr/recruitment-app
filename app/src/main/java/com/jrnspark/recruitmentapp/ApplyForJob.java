package com.jrnspark.recruitmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrnspark.recruitmentapp.utils.Tools;

public class ApplyForJob extends AppCompatActivity {
    EditText name, phoneNumber, email, resume;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_job);

        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        resume = findViewById(R.id.resume);
        apply = findViewById(R.id.apply);

        String jobId = getIntent().getStringExtra("jobId");

        apply.setOnClickListener((view) -> {
            if (name.getText().toString().length() == 0 || phoneNumber.getText().toString().length() == 0 || email.getText().toString().length() == 0 || resume.getText().toString().length() == 0) {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            String sName = name.getText().toString();
            String sPhone = phoneNumber.getText().toString();
            String sEmail = email.getText().toString();
            String sResume = resume.getText().toString();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference applicantref = databaseReference.child("jobs").child(jobId).child("Applicants").push();
            applicantref.child("Name").setValue(sName);
            applicantref.child("Phone Number").setValue(sPhone);
            applicantref.child("Email Address").setValue(sEmail);
            applicantref.child("Resume").setValue(sResume);

            name.setText("");
            phoneNumber.setText("");
            email.setText("");
            resume.setText("");

            Toast.makeText(this,"Application posted Successfully",Toast.LENGTH_SHORT).show();

        });

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Apply for Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.amber_600);
    }
}