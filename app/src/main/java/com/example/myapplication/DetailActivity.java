package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivProductImage;
    private TextView tvProductName, tvProductPrice, txtGenre, txtType;
    private Button btnAddToCart;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivProductImage = findViewById(R.id.iv_product_detail_image);
        tvProductName = findViewById(R.id.tv_product_detail_name);
        tvProductPrice = findViewById(R.id.tv_product_detail_price);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        txtGenre = findViewById(R.id.genre);
        txtType = findViewById(R.id.product_type);

        // Get Data from Intent
        Intent intent = getIntent();
        product = getIntent().getParcelableExtra("product");

        // Set Data to Views
        tvProductName.setText(product.getName());
        tvProductPrice.setText(product.getPrice());
        txtGenre.setText("• " + product.getGenre());
        txtType.setText("• " + product.getType());
        Glide.with(this).load(product.getImagePath()).into(ivProductImage);

        // Add to Cart Button Click Listener
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager.getInstance().addToCart(product);
                Toast.makeText(DetailActivity.this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
                // You can add logic here to store this product in a cart database or shared preferences.
            }
        });
    }
}
