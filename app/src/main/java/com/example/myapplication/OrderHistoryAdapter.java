package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.data.Product;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private final Activity mContext;
    private final List<Product> items;

    public OrderHistoryAdapter(Activity mContext, List<Product> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.MyViewHolder holder, int position) {
        holder.itemTitle.setText(items.get(position).getName());
        holder.itemPrice.setText(items.get(position).getPrice());
        holder.itemDate.setText(items.get(position).getPurchasedDate());
        Glide.with(holder.itemView.getContext()).load(items.get(position).getImagePath()).into(holder.itemImage);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle, itemPrice, itemDate;
        ImageView itemImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.txt_item_purchased_title);
            itemPrice = itemView.findViewById(R.id.txt_item__price);
            itemDate = itemView.findViewById(R.id.txt_item_purchased_date);
            itemImage = itemView.findViewById(R.id.purchased_item_image);
        }
    }
}
