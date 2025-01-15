package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.utils.DataHelper;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private ImageView ivSearch, ivCart;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvProducts = findViewById(R.id.rv_products);
        ivSearch = findViewById(R.id.iv_search);
        ivCart = findViewById(R.id.iv_cart);
        productList = DataHelper.loadProducts(this);
        Log.e("HomeAtivity", "product list " + productList);

        // Setup RecyclerView
        productAdapter = new ProductAdapter(this, productList);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        rvProducts.setAdapter(productAdapter);

        // Search Button
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // Cart Button
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }
}
