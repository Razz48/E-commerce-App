package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private ArrayList<Product> cartItems;
    private CartUpdateListener listener;

    public interface CartUpdateListener {
        void onCartUpdated(double updatedTotalPrice);
    }

    public CartAdapter(Context context, ArrayList<Product> cartItems, CartUpdateListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);

        holder.tvProductName.setText(product.getName());
        double priceValue;
        try {
            priceValue = Double.parseDouble(product.getPrice().replace("$", "").trim());
        } catch (NumberFormatException e) {
            priceValue = 0.0; // Default to 0 if parsing fails
        }

        String formattedPrice = String.format("Price: $%.2f", priceValue);

        holder.tvProductPrice.setText(formattedPrice);
        //holder.ivProductImage.setImageResource(product.getImageResId());

        Glide.with(holder.ivProductImage.getContext())
                .load(product.getImageResId())
                .into(holder.ivProductImage);

        // Handle remove from cart
        holder.ivRemoveItem.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            updateTotalPrice();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void updateTotalPrice() {
        double total=0.0;
        for (Product item : cartItems) {
            try {
                total += Double.parseDouble(item.getPrice().replace("$", "").trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle the error (e.g., skip this product or set its price to 0)
                total += 0.0;
            }
        }
        listener.onCartUpdated(total);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProductImage, ivRemoveItem;
        TextView tvProductName, tvProductPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.iv_cart_item_image);
            tvProductName = itemView.findViewById(R.id.tv_cart_item_name);
            tvProductPrice = itemView.findViewById(R.id.tv_cart_item_price);
            ivRemoveItem = itemView.findViewById(R.id.iv_cart_item_remove);
        }
    }
}

