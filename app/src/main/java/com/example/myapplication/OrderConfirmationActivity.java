package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmationActivity extends AppCompatActivity {

    private Button btnReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        btnReturnHome = findViewById(R.id.btn_return_home);

        // Retrieve data from Intent
        String address = getIntent().getStringExtra("ADDRESS");
        String paymentMethod = getIntent().getStringExtra("PAYMENT_METHOD");

        TextView tvOrderMessage = findViewById(R.id.tv_order_message);
        tvOrderMessage.setText("Thank you for shopping with us!\n\n" +
                "Address: " + address + "\n" +
                "Payment Method: " + paymentMethod);

        // Navigate back to the Home Screen
        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

}
