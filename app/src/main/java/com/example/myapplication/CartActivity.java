package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private  TextView tvTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;
    private ArrayList<Product> cartItems;
    private double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCartItems = findViewById(R.id.rv_cart_items);
        tvTotalPrice = findViewById(R.id.tv_cart_total_price);
        btnCheckout = findViewById(R.id.btn_checkout);

        // Get Cart Items (This can be retrieved from a database or shared preferences)
        cartItems = (ArrayList<Product>) CartManager.getInstance().getCartItems();

        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
        } else {
            // Calculate Total Price
            for (Product item : cartItems) {
                try {
                    totalPrice += Double.parseDouble(item.getPrice().replace("$", "").trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            tvTotalPrice.setText(String.format("Total: $%.2f", totalPrice));

            // Set up RecyclerView
            cartAdapter = new CartAdapter(this, cartItems, updatedTotalPrice -> {
                totalPrice = updatedTotalPrice;
                tvTotalPrice.setText(String.format("Total: $%.2f", totalPrice));
            });
            rvCartItems.setLayoutManager(new LinearLayoutManager(this));
            rvCartItems.setAdapter(cartAdapter);
        }


        // Checkout Button Click Listener
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                intent.putExtra("TOTAL_PRICE", totalPrice);
                startActivity(intent);
            }
        });

    }
}
