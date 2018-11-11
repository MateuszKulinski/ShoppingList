package com.example.komputer.shoppinglist.pages.newlists;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.base.BaseFragment;

public class NewList extends BaseFragment {
    private Button addBtn, saveBtn;
    private EditText listName, productName, productPrice;

    public static NewList newInstance() {
        return new NewList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_list, container, false);

        findViews(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findViews(View view) {
        addBtn = view.findViewById(R.id.new_list_add_product);
        saveBtn = view.findViewById(R.id.new_list_save);
        listName = view.findViewById(R.id.new_list_list_name);
        productName = view.findViewById(R.id.new_list_product_name);
        productPrice = view.findViewById(R.id.new_list_product_price);
    }
}
