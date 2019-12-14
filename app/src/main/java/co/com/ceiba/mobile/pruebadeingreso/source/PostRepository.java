package co.com.ceiba.mobile.pruebadeingreso.source;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.source.local.PostDao;
import co.com.ceiba.mobile.pruebadeingreso.source.local.StoreDatabaseHelper;
import co.com.ceiba.mobile.pruebadeingreso.source.local.UserDao;
import co.com.ceiba.mobile.pruebadeingreso.source.remote.RetrofitInstance;
import co.com.ceiba.mobile.pruebadeingreso.source.remote.services.DataServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {

    private List<Post> posts = new ArrayList<>();

    public PostDao postDao;

    private MutableLiveData<List<Post>> mutableLiveData = new MutableLiveData<>();

    private ProgressDialog progressDialog;

    public PostRepository(Application application) {
        StoreDatabaseHelper storeDatabaseHelper = StoreDatabaseHelper.getInstance(application);
        postDao = storeDatabaseHelper.postDao();
    }

    public LiveData<List<Post>> getLiveData(final Map<String, Integer> params, Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getString(R.string.generic_message_progress));
        progressDialog.show();

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Post> postList = postDao.getPost(params.get("userId"));
                if(postList.size() > 0){
                    mutableLiveData.postValue(postList);
                    progressDialog.dismiss();
                }else {
                    DataServices postsDataServices = RetrofitInstance.getDataServices();


                    Call<List<Post>> call = postsDataServices.getPosts(params);

                    call.enqueue(new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                            if(response.isSuccessful()){
                                List<Post> resultPosts = response.body();
                                if(resultPosts != null){

                                    for (Post item: resultPosts){
                                        new InsertPostAsyncTask(postDao).execute(item);
                                    }
                                    posts = response.body();
                                    mutableLiveData.postValue(posts);

                                }

                                progressDialog.dismiss();

                            }


                        }

                        @Override
                        public void onFailure(Call<List<Post>> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });



        return mutableLiveData;
    }

    private  static  class InsertPostAsyncTask extends AsyncTask<Post, Void, Void> {

        private PostDao postDao;

        public InsertPostAsyncTask(PostDao postDao){
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {

            postDao.insert(posts[0]);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println("Guardado Post");
        }
    }

}
