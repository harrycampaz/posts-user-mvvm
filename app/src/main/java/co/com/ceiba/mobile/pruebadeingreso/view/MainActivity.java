package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;

import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.view.adapter.UserAdapter;
import co.com.ceiba.mobile.pruebadeingreso.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextSearch);

        userList = new ArrayList<>();

        mainActivityViewModel =  ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainActivityViewModel.getAllUser(this).observeForever(new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if(users != null){
                    userList.addAll(users);
                     showRecyclerView();
                 }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                userAdapter.getFilter().filter(charSequence.toString().trim());


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                userAdapter.getFilter().filter(charSequence.toString().trim());


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void showRecyclerView(){

        recyclerView = findViewById(R.id.recyclerViewSearchResults);

        userAdapter = new UserAdapter(this, userList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();

    }

}