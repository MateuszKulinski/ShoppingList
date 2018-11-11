package com.example.komputer.shoppinglist.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {
    @Nullable
    NavigationInterface navigationInterface;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            navigationInterface = (NavigationInterface) context;
        } catch (Exception ex) {
            throw new IllegalStateException("Activity must implement correct navigation interface");
        }
    }

    @Nullable
    public NavigationInterface getNavigationInterface() {
        return navigationInterface;
    }
}
