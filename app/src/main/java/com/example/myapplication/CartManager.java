package com.example.myapplication;


import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems;

    // Private constructor for singleton
    private CartManager() {
        cartItems = new ArrayList<>();
    }

    // Singleton instance getter
    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Add product to cart
    public void addToCart(Product product) {
        cartItems.add(product);
    }

    // Remove product from cart
    public void removeFromCart(Product product) {
        cartItems.remove(product);
    }

    // Clear all cart items
    public void clearCart() {
        cartItems.clear();
    }

    // Get all cart items
    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems); // Return a copy to avoid modification outside
    }

    // Get total cart items count
    public int getCartItemCount() {
        return cartItems.size();
    }
}
