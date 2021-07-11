package com.example.homework1pt3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView app_title;
    Button add_entry;
    RecyclerView recycler_view;
    AdapterHelper adapter_helper;
    ArrayList<Item> item_list;
    ItemDataSource ds;

    // Checking for button press to add new entry.
    private final View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == add_entry) {
                transferActivity();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuring widgets.
        app_title = (TextView) findViewById(R.id.app_title);
        add_entry = (Button) findViewById(R.id.add_entry);
        add_entry.setOnClickListener(onItemClickListener);

        // Retrieving items in database.
        ds = new ItemDataSource(this);
        ds.open();
        item_list = ds.getItems();
        ds.close();

        // Preparing recycler view and adapter for organized display.
        recycler_view = findViewById(R.id.recycler_view);
        adapter_helper = new AdapterHelper(this, item_list);
        recycler_view.setAdapter(adapter_helper);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter_helper.adapter_setOnItemClickListener(onItemClickListener);
    }


    // Starts new activity to add entry to database.
    public void transferActivity() {
        Intent intent = new Intent(this, AddInfoActivity.class);
        this.startActivity(intent);
    }

}