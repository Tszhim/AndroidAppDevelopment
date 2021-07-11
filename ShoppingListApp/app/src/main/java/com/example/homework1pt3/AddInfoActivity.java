package com.example.homework1pt3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class AddInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private TextView empty_error;
    private TextView overflow_error;
    private TextView unexpected_error;
    private EditText item_category;
    private EditText item_name;
    private EditText item_price;
    private EditText item_description;
    private CheckBox item_purchased;
    private Button add_data;
    private Button cancel;
    private ItemDataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info2);

        // Setting up all widgets and data source in activity.
        title = (TextView) findViewById(R.id.add_item_title);
        empty_error = (TextView) findViewById(R.id.empty_input_error_message);
        overflow_error = (TextView) findViewById(R.id.overflow_input_error_message);
        unexpected_error = (TextView) findViewById(R.id.unexpected_error_message);
        item_category = (EditText) findViewById(R.id.add_item_category);
        item_name = (EditText) findViewById(R.id.add_item_name);
        item_price = (EditText) findViewById(R.id.add_item_price);
        item_description = (EditText) findViewById(R.id.add_item_description);
        item_purchased = (CheckBox) findViewById(R.id.add_item_purchased);
        add_data = (Button) findViewById(R.id.add_data_button);
        add_data.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel_add_button);
        cancel.setOnClickListener(this);

        ds = new ItemDataSource(this);
    }

    // Listening for button clicks. If add data button is clicked, insert entry into database then return to main activity. If the cancel
    // button is clicked, return to main activity without inserting.
    @Override
    public void onClick(View v) {
        if (v == add_data) {
            addData();
        } else if (v == cancel) {
            exit();
        }
    }

    public void addData() {
        // Checking if any inputs are empty. If not empty, insert data into database then exit, otherwise, display error message.
        if (dataComplete()) {
            if(validLengths())
            {
                int item_purchase_status = 0;
                if (!item_purchased.isChecked()) {
                    item_purchase_status = 1;
                }

                ds.open();
                boolean success = ds.appendEntry(new Item(0, item_name.getText().toString(), item_category.getText().toString(),
                        item_description.getText().toString(), Double.parseDouble(item_price.getText().toString()), item_purchase_status));
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
            {   empty_error.setVisibility(empty_error.INVISIBLE);
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