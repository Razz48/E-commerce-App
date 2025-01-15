package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.data.Product;
import com.example.myapplication.databinding.FragmentOrderHistoryBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class OrderHistoryFragment extends Fragment {
    private FragmentOrderHistoryBinding binding;
    private OrderHistoryAdapter orderHistoryAdapter;
    private List<Product> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("OrderHistory", requireActivity().MODE_PRIVATE);
        String productsJson = sharedPreferences.getString("orders", "No products yet.");
        if (!productsJson.equals("No products yet.")) {
            // Parse the JSON string to a list of Product objects
            Gson gson = new Gson();
            Type productListType = new TypeToken<List<Product>>() {}.getType();
            productList = gson.fromJson(productsJson, productListType);
            binding.emptyView.setVisibility(View.GONE);
            binding.rvPurchasedItems.setVisibility(View.VISIBLE);


            orderHistoryAdapter = new OrderHistoryAdapter(requireActivity(),productList);
            binding.rvPurchasedItems.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvPurchasedItems.setAdapter(orderHistoryAdapter);
        }
        else{
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.rvPurchasedItems.setVisibility(View.GONE);
        }




    }
}