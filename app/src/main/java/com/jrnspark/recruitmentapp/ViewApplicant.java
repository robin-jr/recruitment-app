package com.jrnspark.recruitmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrnspark.recruitmentapp.utils.Job;
import com.jrnspark.recruitmentapp.utils.People;
import com.jrnspark.recruitmentapp.utils.Tools;

import java.util.ArrayList;

//import java.util.List;

public class ViewApplicant extends AppCompatActivity {
    private static final String TAG = "ListBasic";

    private View parent_view;


    ArrayList<People> items;

    private RecyclerView recyclerView;
    private AdapterListPeople mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        parent_view = findViewById(android.R.id.content);


        initToolbar();
        initComponent();


    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Posts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

//        List<People> items = DataGenerator.getPeopleData(this);
//        items.addAll(DataGenerator.getPeopleData(this));
//        items.addAll(DataGenerator.getPeopleData(this));

        items = new ArrayList<>();
        populateItems();

        //set data and list adapter
        mAdapter = new AdapterListPeople(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListPeople.OnItemClickListener() {
            @Override
            public void onItemClick(View view, People obj, int position) {
                Intent intent = new Intent(ViewApplicant.this, AdminDetailView.class);
                intent.putExtra("name", obj.name);
                intent.putExtra("email", obj.email);
                intent.putExtra("phoneNumber", obj.phoneNumber);
                intent.putExtra("id", obj.id);

                startActivity(intent);
            }

        });

    }

    private boolean isInPeople(People people) {
        for (People j : items) {
            if (j.id.equals(people.id))
                return true;
        }
        return false;
    }


    private void populateItems() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        People people = new People(ds.child("Name").getValue().toString(), ds.child("Email Address").getValue().toString(), ds.child("Phone Number").getValue().toString(), ds.child("Resume").getValue().toString(), ds.getKey());
                        if (isInPeople(people))
                            return;
                        items.add(people);
                        mAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: in");
            }
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//       getMenuInflater().inflate(R.menu.menu_search_setting, menu);
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