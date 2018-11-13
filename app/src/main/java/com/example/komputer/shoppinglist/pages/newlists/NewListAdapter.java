package com.example.komputer.shoppinglist.pages.newlists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.database.ProductItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NewListAdapter extends RecyclerView.Adapter<NewListAdapter.ViewHolder> {
    int primaryColor, secondaryColor;
    private List<ProductItem> list;
    private WeakReference<NewListInterface> interactionsNew;
    private WeakReference<DetailsListInterface> interactionsDetails;
    private Context context;


    public NewListAdapter(List<ProductItem> list, NewListInterface interactionsNew) {
        this.interactionsNew = new WeakReference<>(interactionsNew);
        if (list != null) {
            this.list = list;
        } else {
            list = new ArrayList<>();
        }
    }

    public NewListAdapter(List<ProductItem> list, DetailsListInterface interactionsDetails) {
        this.interactionsDetails = new WeakReference<>(interactionsDetails);
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
        View view = inflater.inflate(R.layout.new_list_item, parent, false);
        primaryColor = parent.getResources().getColor(R.color.primary_text);
        secondaryColor = parent.getResources().getColor(R.color.secondary_text);
        context = parent.getContext();

        return new NewListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ProductItem item = list.get(position);

        holder.prodName.setText(item.getProductName());
        String helper = holder.itemView.getResources().getString(R.string.quantity) + ": ";
        holder.prodQuantity.setText(helper + item.getProductQuantity());
        helper = holder.itemView.getResources().getString(R.string.price) + ": ";
        holder.prodPrice.setText(helper + item.getProductPrice());

        if (interactionsDetails != null) {
            holder.prodTrash.setImageResource(R.drawable.buy);
            if (item.getBought() == 1) {
                setRoot(holder, true);
            } else {
                setRoot(holder, false);
            }
        }
        holder.prodTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interactionsNew != null) {
                    interactionsNew.get().deleteProduct(item.getProductID());
                }
                if (interactionsDetails != null) {
                    if (item.getBought() == 0) {
                        setRoot(holder, true);
                        interactionsDetails.get().bought(item.getProductID(), 1);
                        item.setBought(1);
                    } else {
                        setRoot(holder, false);
                        interactionsDetails.get().bought(item.getProductID(), 0);
                        item.setBought(0);
                    }
                }
            }
        });
    }

    private void setRoot(ViewHolder holder, Boolean b) {
        if (b) {
            holder.root.setBackgroundResource(R.drawable.button_background);
            holder.prodTrash.setImageResource(R.drawable.arrow);
            holder.prodName.setTextColor(primaryColor);
            holder.prodQuantity.setTextColor(primaryColor);
            holder.prodPrice.setTextColor(primaryColor);
        } else {
            holder.root.setBackgroundResource(R.drawable.button_background_unf);
            holder.prodTrash.setImageResource(R.drawable.buy);
            holder.prodName.setTextColor(secondaryColor);
            holder.prodQuantity.setTextColor(secondaryColor);
            holder.prodPrice.setTextColor(secondaryColor);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface NewListInterface {
        void deleteProduct(int productID);
    }

    public interface DetailsListInterface {
        void bought(int productID, int bought);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView prodName, prodQuantity, prodPrice;
        ImageView prodTrash;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            prodName = itemView.findViewById(R.id.new_list_item_prod_name);
            prodQuantity = itemView.findViewById(R.id.new_list_item_prod_quantity);
            prodPrice = itemView.findViewById(R.id.new_list_item_prod_price);
            prodTrash = itemView.findViewById(R.id.new_list_item_prod_trach);
        }
    }
}