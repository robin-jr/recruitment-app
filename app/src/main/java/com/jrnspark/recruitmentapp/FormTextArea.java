package com.jrnspark.recruitmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrnspark.recruitmentapp.utils.Tools;

public class FormTextArea extends AppCompatActivity {
    private EditText jobName, jobDescription, contactEmail;
    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_text_area);

        jobName = findViewById(R.id.jobName);
        jobDescription = findViewById(R.id.jobDescription);
        contactEmail = findViewById(R.id.contactEmail);
        post = findViewById(R.id.post);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        post.setOnClickListener((view) -> {
            if (jobName.getText().toString().length() == 0 || jobDescription.getText().toString().length() == 0 || contactEmail.getText().toString().length() == 0) {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            DatabaseReference ref = databaseReference.child("jobs").push().getRef();
            ref.child("jobName").setValue(jobName.getText().toString());
            ref.child("jobDescription").setValue(jobDescription.getText().toString());
            ref.child("contactEmail").setValue(contactEmail.getText().toString());

            Toast.makeText(this, "Post Added successfully", Toast.LENGTH_SHORT).show();
            jobName.setText("");
            jobDescription.setText("");
            contactEmail.setText("");
        });

        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create new Job Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.amber_600);

//        Tools.setSystemBarColor(this, R.color.red_600);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_done, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}