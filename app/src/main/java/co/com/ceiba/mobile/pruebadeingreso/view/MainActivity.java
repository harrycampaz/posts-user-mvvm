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

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;


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

    RelativeLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextSearch);
        content = findViewById(R.id.content);

        final View to_add = getLayoutInflater().inflate(R.layout.empty_view,
                content,false);

        to_add.setVisibility(View.VISIBLE);
        //content.addView(to_add);

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

                userAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if(userAdapter.getItemCount() < 1){
                             if(content.getChildCount() < 3){
                                content.addView(to_add);

                            }

                        }else {
                            if(content.getChildCount() > 2){
                                content.removeView(to_add);
                            }

                        }
                    }
                });

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