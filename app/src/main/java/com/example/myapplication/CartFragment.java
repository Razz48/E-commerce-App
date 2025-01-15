package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.data.Product;
import com.example.myapplication.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private ArrayList<Product> cartItems;
    private double totalPrice = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get Cart Items (This can be retrieved from a database or shared preferences)
        cartItems = (ArrayList<Product>) CartManager.getInstance().getCartItems();

        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
        } else {
            // Calculate Total Price
            for (Product item : cartItems) {
                try {
                    totalPrice += Double.parseDouble(item.getPrice().replace("$", "").trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            // Set total price text
            binding.tvCartTotalPrice.setText(String.format("Total: $%.2f", totalPrice));

            // Set up RecyclerView
            cartAdapter = new CartAdapter(requireContext(), cartItems, updatedTotalPrice -> {
                totalPrice = updatedTotalPrice;
                binding.tvCartTotalPrice.setText(String.format("Total: $%.2f", totalPrice));
            });
            binding.rvCartItems.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.rvCartItems.setAdapter(cartAdapter);
        }

        // Checkout Button Click Listener
        binding.btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CheckoutActivity.class);
            intent.putExtra("TOTAL_PRICE", totalPrice);
            intent.putParcelableArrayListExtra("CART_ITEMS", cartItems);
            startActivity(intent);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Nullify the binding to avoid memory leaks
        binding = null;
    }
}
