package com.example.homework1pt3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHelper extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;
    private View.OnClickListener click_listener;

    public AdapterHelper(Context ct, ArrayList<Item> items) {
        this.context = ct;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout_inflater = LayoutInflater.from(parent.getContext());
        View view = layout_inflater.inflate(R.layout.item_box, parent, false);
        return new ViewHolder(view);
    }

    // Handling widget display in viewholder.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item curr_item = items.get(position);
        ViewHolder v = (ViewHolder) holder;

        // Configuring information on display.
        v.item_name.setText(curr_item.name);
        v.item_image.setImageResource(curr_item.image);
        v.item_description.setText(curr_item.description);
        v.item_price.setText("$" + curr_item.estimated_price);
        if (curr_item.purchase_status == 0) {
            v.purchase_status.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void adapter_setOnItemClickListener(View.OnClickListener itemClickListener) {
        click_listener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView item_name;
        TextView item_description;
        TextView item_price;
        CheckBox purchase_status;
        Button edit_button;
        Button hide_details_button;
        Button unhide_details_button;

        public ViewHolder(@NonNull View itemView) {
            // Setting up all widgets in a single viewholder.
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_description = itemView.findViewById(R.id.item_description);
            item_price = itemView.findViewById(R.id.item_price);
            purchase_status = itemView.findViewById(R.id.purchase_status);
            purchase_status.setEnabled(false);
            edit_button = itemView.findViewById(R.id.edit);
            hide_details_button = itemView.findViewById(R.id.hide_details);
            unhide_details_button = itemView.findViewById(R.id.unhide_details_button);

            // If edit button is clicked, start new activity to edit entry.
            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v == edit_button) {
                        Intent intent = new Intent(context, EditInfoActivity.class);
                        intent.putExtra("item_index", getAdapterPosition());
                        intent.putExtra("item_list", items);
                        context.startActivity(intent);
                    }
                }
            });

            // If hide details button is clicked, hide all widgets except name.
            hide_details_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v == hide_details_button) {
                        item_image.setVisibility(item_image.GONE);
                        item_description.setVisibility(item_description.GONE);
                        item_price.setVisibility(item_price.GONE);
                        purchase_status.setVisibility(purchase_status.GONE);
                        edit_button.setVisibility(edit_button.GONE);
                        hide_details_button.setVisibility(hide_details_button.GONE);
                        unhide_details_button.setVisibility(unhide_details_button.VISIBLE);
                    }
                }
            });

            // If unhide details button is clicked, restore all widgets.
            unhide_details_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item_image.setVisibility(item_image.VISIBLE);
                    item_description.setVisibility(item_description.VISIBLE);
                    item_price.setVisibility(item_price.VISIBLE);
                    purchase_status.setVisibility(purchase_status.VISIBLE);
                    edit_button.setVisibility(edit_button.VISIBLE);
                    hide_details_button.setVisibility(hide_details_button.VISIBLE);
                    unhide_details_button.setVisibility(unhide_details_button.GONE);
                }
            });

            itemView.setTag(this);
            itemView.setOnClickListener(click_listener);
        }

    }

}
