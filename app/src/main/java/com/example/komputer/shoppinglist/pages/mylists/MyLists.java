package com.example.komputer.shoppinglist.pages.mylists;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.base.BaseFragment;

public class MyLists extends BaseFragment {
    private RecyclerView recyclerView;

    public static MyLists newInstance() {
        return new MyLists();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_lists, container, false);

        findViews(view);
        return view;
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.my_lists_recycler);
    }
}
