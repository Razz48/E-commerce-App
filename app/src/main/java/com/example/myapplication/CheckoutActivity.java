package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etDeliveryAddress;
    private RadioGroup rgPaymentMethod;
    private TextView tvTotalPriceSummary;
    private Button btnConfirmOrder;

    private double totalPrice;

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
}
