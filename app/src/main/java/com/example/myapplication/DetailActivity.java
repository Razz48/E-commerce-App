package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivProductImage;
    private TextView tvProductName, tvProductPrice;
    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivProductImage = findViewById(R.id.iv_product_detail_image);
        tvProductName = findViewById(R.id.tv_product_detail_name);
        tvProductPrice = findViewById(R.id.tv_product_detail_price);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);

        // Get Data from Intent
        Intent intent = getIntent();
        String productName = intent.getStringExtra("product_name");
        String productPrice = intent.getStringExtra("product_price");
        int productImageResId = intent.getIntExtra("product_image", 0);

        // Set Data to Views
        tvProductName.setText(productName);
        tvProductPrice.setText(productPrice);
        ivProductImage.setImageResource(productImageResId);

        // Add to Cart Button Click Listener
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product(productName, productPrice, productImageResId);
                CartManager.getInstance().addToCart(product);
                Toast.makeText(DetailActivity.this, productName + " added to cart", Toast.LENGTH_SHORT).show();
                // You can add logic here to store this product in a cart database or shared preferences.
            }
        });
    }
}
