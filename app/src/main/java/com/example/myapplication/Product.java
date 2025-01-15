package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {
    private final String name;
    private final String price;
    private final String imagePath;
    private String type;
    private String genre;

    // Constructor with genre
    public Product(String name, String price, String imagePath, String type, String genre) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.type = type;
        this.genre = genre;
    }

    protected Product(Parcel in) {
        name = in.readString();
        price = in.readString();
        imagePath = in.readString();
        type = in.readString();
        genre = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(imagePath);
        dest.writeString(type);
        dest.writeString(genre);
    }
}

