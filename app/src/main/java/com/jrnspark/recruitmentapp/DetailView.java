package com.jrnspark.recruitmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DetailView extends AppCompatActivity {
    TextView jobName, jobDescription,contactEmail;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        jobName = findViewById(R.id.jobName);
        jobDescription = findViewById(R.id.jobDescription);
        contactEmail = findViewById(R.id.contactEmail);

        apply = findViewById(R.id.apply);

        String name = getIntent().getStringExtra("jobName");
        String description = getIntent().getStringExtra("jobDescription");
        String email = getIntent().getStringExtra("contactEmail");
        String jobId = getIntent().getStringExtra("jobId");

        jobName.setText(name);
        contactEmail.setText("Contact : "+email);
        jobDescription.setText(description);

        apply.setOnClickListener((view)->{
            //TODO
            Intent intent = new Intent(DetailView.this,ApplyForJob.class);
            intent.putExtra("jobId",jobId);
            startActivity(intent);
        });
    }
}