package com.example.komputer.shoppinglist.pages.mylists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.database.ListItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MyListsAdapter extends RecyclerView.Adapter<MyListsAdapter.ViewHolder> {
    private List<ListItem> list;
    private WeakReference<MyListInterface> interactions;

    public MyListsAdapter(List<ListItem> list, MyListInterface interactions) {
        this.interactions = new WeakReference<>(interactions);
        if (list != null) {
            this.list = list;
        } else {
            list = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_lists_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItem item = list.get(position);

        holder.name.setText(item.getListName());
        String helper = holder.itemView.getResources().getString(R.string.price) + ": ";
        holder.price.setText(helper + item.getListPrice());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interactions.get() != null) {
                    interactions.get().onClick(item.getListID());
                }
            }
        });

        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interactions.get() != null) {
                    interactions.get().onDelete(item.getListID());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface MyListInterface {
        void onClick(int listID);

        void onDelete(int listID);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView name, price;
        ImageView trash;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            name = itemView.findViewById(R.id.my_lists_item_name);
            price = itemView.findViewById(R.id.my_lists_item_price);
            trash = itemView.findViewById(R.id.my_lists_item_trash);
        }
    }
}
