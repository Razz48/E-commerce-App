package com.example.myapplication;


import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView rvSearchResults;
    private ProductAdapter searchAdapter;
    private List<Product> productList;
    private List<Product> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search_view);
        rvSearchResults = findViewById(R.id.rv_search_results);

        // Get product list from HomeActivity (mock data for now)
        productList = getSampleProducts();
        searchResults = new ArrayList<>();

        // Setup RecyclerView
        searchAdapter = new ProductAdapter(this, searchResults);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResults.setAdapter(searchAdapter);

        // Handle Search Query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterResults(newText);
                return true;
            }
        });
    }

    // Filter products based on search query
    private void filterResults(String query) {
        searchResults.clear();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(product);
            }
        }
        searchAdapter.notifyDataSetChanged();
    }

    // Mock product data
    private List<Product> getSampleProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", "$1200", R.drawable.photos));
        products.add(new Product("Phone", "$800", R.drawable.photos));
        products.add(new Product("Headphones", "$150", R.drawable.photos));
        products.add(new Product("Camera", "$1000", R.drawable.photos));
        products.add(new Product("Watch", "$250", R.drawable.photos));
        return products;
    }
}
