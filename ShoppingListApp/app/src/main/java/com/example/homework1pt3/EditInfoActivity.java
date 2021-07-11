package com.example.homework1pt3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private TextView empty_error;
    private TextView overflow_error;
    private TextView unexpected_error;
    private EditText item_category;
    private EditText item_name;
    private EditText item_price;
    private EditText item_description;
    private CheckBox item_purchased;
    private Button save_data;
    private Button cancel;
    private ArrayList<Item> items;
    private int item_index;
    private ItemDataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Setting up all widgets and data source in activity.
        title = (TextView) findViewById(R.id.edit_item_title);
        empty_error = (TextView) findViewById(R.id.edit_input_empty_error_message);
        overflow_error = (TextView) findViewById(R.id.edit_input_overflow_error_message);
        unexpected_error = (TextView) findViewById(R.id.edit_unexpected_error_message);
        item_category = (EditText) findViewById(R.id.edit_item_category);
        item_name = (EditText) findViewById(R.id.edit_item_name);
        item_price = (EditText) findViewById(R.id.edit_item_price);
        item_description = (EditText) findViewById(R.id.edit_item_description);
        item_purchased = (CheckBox) findViewById(R.id.edit_item_purchased);
        save_data = (Button) findViewById(R.id.save_data_button);
        save_data.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);
        items = this.getIntent().getExtras().getParcelableArrayList("item_list");
        item_index = getIntent().getExtras().getInt("item_index");
        ds = new ItemDataSource(this);

        // Configuring widgets to display information from existing entry.
        Item old_info = items.get(item_index);
        item_category.setText(old_info.category);
        item_name.setText(old_info.name);
        item_price.setText(String.valueOf(old_info.estimated_price));
        item_description.setText(old_info.description);
        if (old_info.purchase_status == 0) {
            item_purchased.setChecked(true);
        }
    }

    // Listening for button clicks. If save data button is clicked, edit entry in database then return to main activity. If the cancel
    // button is clicked, return to main activity without inserting.
    @Override
    public void onClick(View v) {
        if (v == save_data) {
            saveData();
        } else if (v == cancel) {
            exit();
        }
    }

    public void saveData() {
        // Checking if inputs are empty. If not empty, edit entry, then exit, otherwise, display error message.
        if (dataComplete()) {
            if(validLengths())
            {
                Item old_item = items.get(item_index);
                int item_purchase_status = 0;
                if (!item_purchased.isChecked()) {
                    item_purchase_status = 1;
                }

                ds.open();
                Item new_item = new Item(old_item.id, item_name.getText().toString(), item_category.getText().toString(),
                        item_description.getText().toString(), Double.parseDouble(item_price.getText().toString()), item_purchase_status);
                boolean success = ds.editEntry(new_item);
                ds.close();

                if (success) {
                    exit();
                } else {
                    empty_error.setVisibility(empty_error.INVISIBLE);
                    overflow_error.setVisibility(overflow_error.INVISIBLE);
                    unexpected_error.setVisibility(unexpected_error.VISIBLE);
                }
            }
            else
            {
                empty_error.setVisibility(empty_error.INVISIBLE);
                unexpected_error.setVisibility(unexpected_error.INVISIBLE);
                overflow_error.setVisibility(overflow_error.VISIBLE);
            }
        } else {
            unexpected_error.setVisibility(unexpected_error.INVISIBLE);
            overflow_error.setVisibility(overflow_error.INVISIBLE);
            empty_error.setVisibility(empty_error.VISIBLE);
        }
    }

    // Checking if any inputs are empty. If all are filled, return true, otherwise, false.
    public boolean dataComplete() {
        return !item_category.getText().toString().isEmpty() && !item_name.getText().toString().isEmpty()
                && !item_price.getText().toString().isEmpty() && !item_description.getText().toString().isEmpty();
    }

    public boolean validLengths() {
        return item_category.getText().toString().length() <= 20 && item_name.getText().toString().length() <= 20 &&
                item_price.getText().toString().length() <= 20 && item_description.getText().toString().length() <= 10;
    }

    // Returns to main activity.
    public void exit() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}