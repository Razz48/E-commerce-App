package com.example.myapplication;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.data.Product;
import com.example.myapplication.databinding.ActivitySearchBinding;
import com.example.myapplication.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private ProductAdapter searchAdapter;
    private List<Product> productList;
    private List<Product> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productList = DataHelper.loadProducts(this);
        Log.e("SearchActivty", "productList = " + productList);
        searchResults = new ArrayList<>();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.super.onBackPressed();
            }
        });

        // Handle Search Query
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 1) {
                    binding.noResults.setVisibility(View.GONE);
                    binding.searchResults.setVisibility(VISIBLE);
                    filterResults(newText);
                } else {
                    if (searchAdapter != null) searchAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }


    // Filter products based on search query
    private void filterResults(String query) {
        searchResults.clear();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase()) || product.getType().toLowerCase().contains(query.toLowerCase())){
                searchResults.add(product);
            }
        }
        Log.e("SearchActivty", "search results " + searchResults);
        if (!searchResults.isEmpty()) {
            binding.noResults.setVisibility(GONE);
            binding.searchResults.setVisibility(VISIBLE);
            searchAdapter = new ProductAdapter(this, searchResults);
            binding.searchResults.setLayoutManager(new LinearLayoutManager(this));
            binding.searchResults.setAdapter(searchAdapter);
        } else {
            binding.noResults.setVisibility(VISIBLE);
            binding.searchResults.setVisibility(GONE);
        }

    }
}
