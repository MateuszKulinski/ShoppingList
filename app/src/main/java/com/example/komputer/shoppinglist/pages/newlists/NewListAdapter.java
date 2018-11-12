package com.example.komputer.shoppinglist.pages.newlists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.database.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class NewListItem extends RecyclerView.Adapter<NewListItem.ViewHolder> {
    private static double totalPrice = 0;
    private List<ProductItem> list;
    public NewListItem(List<ProductItem> list) {
        if (list != null) {
            this.list = list;
        } else {
            list = new ArrayList<>();
        }
    }

    public static double getTotalPrice() {
        return totalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.new_list_item, parent, false);

        return new NewListItem.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem item = list.get(position);
        totalPrice = +Double.parseDouble(list.get(position).getProductPrice());

        holder.prodName.setText(item.getProductName());
        holder.prodPrice.setText(item.getProductPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView prodName, prodPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            prodName = itemView.findViewById(R.id.new_list_item_prod_name);
            prodPrice = itemView.findViewById(R.id.new_list_item_prod_price);
        }
    }
}