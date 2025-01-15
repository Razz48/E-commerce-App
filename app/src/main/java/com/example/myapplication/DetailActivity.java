package com.example.myapplication;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.data.Product;
import com.example.myapplication.databinding.ActivityDetailBinding;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Get Data from Intent
        product = getIntent().getParcelableExtra("product");

        // Set Data to Views
        binding.tvProductDetailName.setText(product.getName());
        binding.tvProductDetailPrice.setText(product.getPrice());
        binding.genre.setText("• " + product.getGenre());
        binding.productType.setText("• " + product.getType());
        Glide.with(this).load(product.getImagePath()).into(binding.ivProductDetailImage);

        // Add to Cart Button Click Listener
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager.getInstance().addToCart(product);
                Toast.makeText(DetailActivity.this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
                // You can add logic here to store this product in a cart database or shared preferences.
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.super.onBackPressed();
            }
        });
    }



}
