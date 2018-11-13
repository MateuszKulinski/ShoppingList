package com.example.komputer.shoppinglist.pages.mylists;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.base.BaseFragment;
import com.example.komputer.shoppinglist.database.DatabaseHelper;
import com.example.komputer.shoppinglist.database.ListItem;
import com.example.komputer.shoppinglist.pages.mylists.detailsList.DetailsList;

import java.util.List;

public class MyLists extends BaseFragment implements MyListsAdapter.MyListInterface {
    public static String KEY_ID = "id_key";
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private MyListsAdapter adapter;
    private TextView empty;
    private List<ListItem> list;

    public static MyLists newInstance() {
        return new MyLists();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_lists, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        findViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
    }

    private void setAdapter() {
        list = databaseHelper.getListShops();
        adapter = new MyListsAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.my_lists_recycler);
        empty = view.findViewById(R.id.my_lists_empty);
    }

    @Override
    public void onClick(int listID) {
        DetailsList detailsList = DetailsList.newInstance();
        Bundle args = new Bundle();
        args.putInt(KEY_ID, listID);
        detailsList.setArguments(args);
        getNavigationInterface().changeFragment(detailsList);
    }

    @Override
    public void onDelete(int listID) {
        databaseHelper.deleteList(listID);
        Toast.makeText(getContext(), getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
        setAdapter();
    }
}
