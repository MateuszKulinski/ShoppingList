package com.example.komputer.shoppinglist.pages.mylists.detailsList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.base.BaseFragment;
import com.example.komputer.shoppinglist.database.DatabaseHelper;
import com.example.komputer.shoppinglist.database.ProductItem;
import com.example.komputer.shoppinglist.pages.mylists.MyLists;
import com.example.komputer.shoppinglist.pages.newlists.NewListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsList extends BaseFragment implements NewListAdapter.DetailsListInterface {
    private DatabaseHelper databaseHelper;
    private long listID;
    private RecyclerView recyclerView;
    private NewListAdapter adapter;
    private List<ProductItem> list = new ArrayList<>();
    private Button deleteBtn;

    public static DetailsList newInstance() {
        return new DetailsList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_list, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listID = bundle.getInt(MyLists.KEY_ID);
        }

        databaseHelper = new DatabaseHelper(getContext());
        findViews(view);
        setListeners();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
    }

    private void setListeners() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteList();
            }
        });
    }

    private void deleteList() {
        databaseHelper.deleteList(listID);
        Toast.makeText(getContext(), getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
        getNavigationInterface().changeFragment(MyLists.newInstance());
    }

    private void setAdapter() {
        list = databaseHelper.getListProducts(listID);
        adapter = new NewListAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.details_list_recycler_view);
        deleteBtn = view.findViewById(R.id.details_list_delete);
    }

    @Override
    public void bought(int productID, int bought) {
        String text;
        if (bought == 1) {
            text = getResources().getString(R.string.bought);
        } else {
            text = getResources().getString(R.string.deleted);
        }
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        databaseHelper.setBought(productID, bought);
    }
}
