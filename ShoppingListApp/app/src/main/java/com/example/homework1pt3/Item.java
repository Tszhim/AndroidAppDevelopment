package com.example.homework1pt3;

import android.os.Parcel;
import android.os.Parcelable;


public class Item implements Parcelable{
    int id;
    String name;
    String category;
    int image;
    String description;
    double estimated_price;
    int purchase_status;

    public Item(int id, String name, String category, String description, double estimated_price, int purchase_status)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.estimated_price = estimated_price;
        this.purchase_status = purchase_status;
    }

    public void setImage(){
        if(category.equalsIgnoreCase("Food"))
        {
            image = R.drawable.ic_food_icon;
        }
        else if(category.equalsIgnoreCase("Furniture"))
        {
            image = R.drawable.ic_furniture_icon;
        }
        else if(category.equalsIgnoreCase("Electronics"))
        {
            image = R.drawable.ic_electronics_icon;
        }
        else
        {
            image = R.drawable.ic_miscellaneous_icon;
        }
    }

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        description = in.readString();
        estimated_price = in.readDouble();
        purchase_status = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeDouble(estimated_price);
        dest.writeInt(purchase_status);
    }
}
