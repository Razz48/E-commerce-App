package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etDeliveryAddress;
    private RadioGroup rgPaymentMethod;
    private TextView tvTotalPriceSummary;
    private Button btnConfirmOrder;

    private double totalPrice;
    List<Product> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        etDeliveryAddress = findViewById(R.id.et_delivery_address);
        rgPaymentMethod = findViewById(R.id.rg_payment_method);
        tvTotalPriceSummary = findViewById(R.id.tv_total_price_summary);
        btnConfirmOrder = findViewById(R.id.btn_confirm_order);

        // Retrieve total price from Intent
        totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0.0);
        cartItems = getIntent().getParcelableArrayListExtra("CART_ITEMS");

        tvTotalPriceSummary.setText(String.format("Total: $%.2f", totalPrice));

        // Confirm Order Button Click Listener
        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = etDeliveryAddress.getText().toString().trim();
                int selectedPaymentMethodId = rgPaymentMethod.getCheckedRadioButtonId();

                if (address.isEmpty()) {
                    Toast.makeText(CheckoutActivity.this, "Please enter a delivery address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedPaymentMethodId == -1) {
                    Toast.makeText(CheckoutActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedPaymentMethod = findViewById(selectedPaymentMethodId);
                String paymentMethod = selectedPaymentMethod.getText().toString();

                for (Product product : cartItems) {
                    String currentDate = getCurrentDate();
                    product.setPurchasedDate(currentDate);
                }

                saveOrderToHistory(cartItems);


                // Clear Cart
                CartManager.getInstance().clearCart();


                // Navigate to Order Confirmation Screen
                Intent intent = new Intent(CheckoutActivity.this, OrderConfirmationActivity.class);
                intent.putExtra("ADDRESS", address);
                intent.putExtra("PAYMENT_METHOD", paymentMethod);
                startActivity(intent);

                // Finish current activity
                finish();
            }
        });

    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void saveOrderToHistory(List<Product> newOrders) {
        // Get current order history from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("OrderHistory", MODE_PRIVATE);
        String existingOrdersJson = sharedPreferences.getString("orders", "[]");

        // Parse the existing orders to a list
        Gson gson = new Gson();
        Type productListType = new TypeToken<List<Product>>() {}.getType();
        List<Product> existingOrders = gson.fromJson(existingOrdersJson, productListType);

        // Add the new cart items to the existing order history
        existingOrders.addAll(newOrders);

        // Save the updated order history back to SharedPreferences
        String updatedOrdersJson = gson.toJson(existingOrders);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("orders", updatedOrdersJson);
        editor.apply();
    }
}
