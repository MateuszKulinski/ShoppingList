package com.example.komputer.shoppinglist.pages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.base.BaseFragment;
import com.example.komputer.shoppinglist.pages.mylists.MyLists;
import com.example.komputer.shoppinglist.pages.newlists.NewList;

public class Menu extends BaseFragment {
    private Button myListBtn, newListBtn;

    public static Menu newInstance() {
        return new Menu();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);

        findViews(view);
        onClick();
        return view;
    }

    private void onClick() {
        myListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationInterface().changeFragment(MyLists.newInstance());
            }
        });
        newListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationInterface().changeFragment(NewList.newInstance());
            }
        });
    }

    private void findViews(View view) {
        myListBtn = view.findViewById(R.id.menu_fragment_my_list);
        newListBtn = view.findViewById(R.id.menu_fragment_new_list);
    }
}
