package com.example.komputer.shoppinglist.pages.mylists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.komputer.shoppinglist.R;

public class MyListsItem extends RecyclerView.Adapter<MyListsItem.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_lists_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView name, price;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            name = itemView.findViewById(R.id.my_lists_item_name);
            price = itemView.findViewById(R.id.my_lists_item_price);
        }
    }
}
