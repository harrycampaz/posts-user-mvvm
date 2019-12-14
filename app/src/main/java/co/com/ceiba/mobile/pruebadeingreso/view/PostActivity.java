package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.view.adapter.PostAdapter;
import co.com.ceiba.mobile.pruebadeingreso.viewmodel.MainActivityViewModel;
import co.com.ceiba.mobile.pruebadeingreso.viewmodel.PostActivityViewModel;

public class PostActivity extends AppCompatActivity {

    public   static final  String USER_INTENT ="user";
    private PostActivityViewModel postActivityViewModel;
    private RecyclerView recyclerView;

    private PostAdapter postAdapter;

    private User user;

    public TextView name, phone, email;
    Map<String, Integer> params = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        Intent intent = getIntent();

        if(intent.hasExtra(USER_INTENT)){
            user = (User) intent.getSerializableExtra(USER_INTENT);
            name.setText(user.getName());
            phone.setText(user.getPhone());
            email.setText(user.getEmail());
            params.put("userId", user.getId());
        }



        postActivityViewModel = ViewModelProviders.of(this).get(PostActivityViewModel.class);

        postActivityViewModel.getAllPost(params, this).observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                if(posts != null){
                    showRecyclerView(posts);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void showRecyclerView(List<Post> posts){
        recyclerView = findViewById(R.id.recyclerViewPostsResults);
        postAdapter = new PostAdapter(this, posts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PostActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();
    }

}
