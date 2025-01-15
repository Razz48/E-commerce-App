package com.example.myapplication.utils;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    public static List<Product> loadProducts(Context context) {
        List<Product> productList = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open("products.json");
            InputStreamReader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            productList = gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType());

            reader.close();

            Log.d("DataHelper", "Loaded products: " + productList.size());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DataHelper", "Error loading products: " + e.getMessage());
        }
        return productList;
    }
}
