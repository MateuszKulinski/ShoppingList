package com.example.komputer.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.komputer.shoppinglist.base.BaseFragment;
import com.example.komputer.shoppinglist.base.NavigationInterface;
import com.example.komputer.shoppinglist.pages.Menu;

public class MainActivity extends AppCompatActivity implements NavigationInterface {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment(Menu.newInstance());
    }

    @Override
    public void changeFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(fragment.toString());
        transaction.replace(R.id.main_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
