package com.example.komputer.shoppinglist.pages.newlists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komputer.shoppinglist.R;
import com.example.komputer.shoppinglist.base.BaseFragment;
import com.example.komputer.shoppinglist.database.DatabaseHelper;
import com.example.komputer.shoppinglist.database.ProductItem;
import com.example.komputer.shoppinglist.pages.Menu;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class NewList extends BaseFragment implements TextWatcher, NewListAdapter.NewListInterface {
    private DatabaseHelper databaseHelper;
    private Button addProdBtn, saveBtn, addListBtn, sendTextBtn;
    private TextView listNameTV;
    private EditText listName, productName, productPrice, productQuantity;
    private TextInputLayout inputLayout;
    private Long shopID;
    private RecyclerView recyclerView;
    private ScrollView scrollView;
    private NewListAdapter adapter;
    private List<ProductItem> list = new ArrayList<>();

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

    private void initDatabase() {
        databaseHelper = new DatabaseHelper(getContext());
    }

    private boolean addList() {
        shopID = databaseHelper.insertListData(listName.getText().toString().trim());
        if (shopID != -1) {
            Toast.makeText(getContext(), getResources().getString(R.string.saved), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void addProduct() {
        int quantity;
        float price = 0;
        if (productQuantity.getText().toString().trim().length() > 0) {
            quantity = Integer.valueOf(productQuantity.getText().toString().trim());
        } else {
            quantity = 1;
        }
        if (productPrice.getText().toString().trim().length() > 0) {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#.##", decimalFormatSymbols);
            price = Float.valueOf(productPrice.getText().toString().trim());
            String priceStr = decimalFormat.format(price);
            price = Float.parseFloat(priceStr);
            price *= quantity;
        }

        Boolean success = databaseHelper.insertProdData(shopID, productName.getText().toString().trim(), quantity, price);
        if (success) {
            setBtnColor(saveBtn, true);
            setBtnColor(sendTextBtn, true);
            Toast.makeText(getContext(), getResources().getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
        }
        productName.setText("");
        productName.requestFocus();
        productQuantity.setText("");
        productPrice.setText("");
        getAdapter();
        showSoft(productName);
    }

    private void sendText() {
        String text = listNameTV.getText() + " " + databaseHelper.getTotalPrice() + "\n";
        for (ProductItem item : list) {
            text += item.getProductName() + ", " + getResources().getText(R.string.quantity) + ": " + item.getProductQuantity() + ", " + getResources().getText(R.string.price) + ": " + (item.getProductPrice() < 0.1 ? 0 : item.getProductPrice()) + "\n";
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void saveList() {
        databaseHelper.getTotalPrice();
        Toast.makeText(getContext(), getResources().getString(R.string.saved), Toast.LENGTH_SHORT).show();
        getNavigationInterface().changeFragment(Menu.newInstance());
    }

    private void setListeners() {
        listName.addTextChangedListener(this);
        productName.addTextChangedListener(this);

        addProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveList();
            }
        });
        addListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatabase();
                if (addList()) {
                    setVisible();
                    showSoft(productName);
                }
            }
        });
        sendTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText();
            }
        });
    }

    private void setVisible() {
        inputLayout.setVisibility(View.GONE);
        addListBtn.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        addProdBtn.setVisibility(View.VISIBLE);
        listNameTV.setVisibility(View.VISIBLE);
        productName.setVisibility(View.VISIBLE);
        productQuantity.setVisibility(View.VISIBLE);
        productPrice.setVisibility(View.VISIBLE);
        listNameTV.setText(listName.getText().toString().trim());
        scrollView.setVisibility(View.VISIBLE);
    }

    private void findViews(View view) {
        listNameTV = view.findViewById(R.id.new_list_list_name_tv);
        addProdBtn = view.findViewById(R.id.new_list_add_product);
        saveBtn = view.findViewById(R.id.new_list_save);
        addListBtn = view.findViewById(R.id.new_list_add_list);
        sendTextBtn = view.findViewById(R.id.new_list_send);
        listName = view.findViewById(R.id.new_list_list_name);
        inputLayout = view.findViewById(R.id.new_list_list_name_widget);
        productName = view.findViewById(R.id.new_list_product_name);
        productQuantity = view.findViewById(R.id.new_list_product_quantity);
        productPrice = view.findViewById(R.id.new_list_product_price);
        recyclerView = view.findViewById(R.id.new_list_recycler_view);
        scrollView = view.findViewById(R.id.new_list_scroll_view);
        showSoft(listName);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void showSoft(EditText editText) {
        editText.requestFocus();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (productName.getText().length() > 0) {
            setBtnColor(addProdBtn, true);
        } else {
            setBtnColor(addProdBtn, false);
        }
        if (listName.getText().length() > 0) {
            setBtnColor(addListBtn, true);
        } else {
            setBtnColor(addListBtn, false);
        }
    }

    private void setBtnColor(Button btn, boolean selected) {
        if (selected) {
            btn.setEnabled(true);
            btn.setTextColor(getResources().getColor(R.color.primary_text));
            btn.setBackgroundResource(R.drawable.button_background);
        } else {
            btn.setEnabled(false);
            btn.setTextColor(getResources().getColor(R.color.secondary_text));
            btn.setBackgroundResource(R.drawable.button_background_unf);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void deleteProduct(int productID) {
        databaseHelper.deleteProduct(productID);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
        getAdapter();
        if (list.isEmpty()) {
            setBtnColor(saveBtn, false);
            setBtnColor(sendTextBtn, false);
        }
    }

    public void getAdapter() {
        list = databaseHelper.getListProducts(shopID);
        if (list != null) {
            adapter = new NewListAdapter(list, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
