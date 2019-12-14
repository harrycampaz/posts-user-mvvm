package co.com.ceiba.mobile.pruebadeingreso.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.source.PostRepository;


public class PostActivityViewModel extends AndroidViewModel {

    private PostRepository postRepository;



    public PostActivityViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);

    }

    public LiveData<List<Post>> getAllPost(Map<String, Integer> params, Context context){
        return postRepository.getLiveData(params, context);
    }

}
