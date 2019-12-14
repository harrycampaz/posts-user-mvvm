package co.com.ceiba.mobile.pruebadeingreso.source;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.source.local.StoreDatabaseHelper;
import co.com.ceiba.mobile.pruebadeingreso.source.local.UserDao;
import co.com.ceiba.mobile.pruebadeingreso.source.remote.RetrofitInstance;
import co.com.ceiba.mobile.pruebadeingreso.source.remote.services.DataServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    private MutableLiveData<List<User>> mutableLiveData = new MutableLiveData<>();

    private ProgressDialog progressDialog;

    public UserDao userDao;


    public UserRepository(Application application) {
        StoreDatabaseHelper storeDatabaseHelper = StoreDatabaseHelper.getInstance(application);
        userDao = storeDatabaseHelper.userDao();


    }

    public LiveData<List<User>> getLiveData(Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Por favor espere...");
        progressDialog.show();

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<User> userList = userDao.getUsers();
                if (userList.size() > 0) {
                    System.out.println("Tamaño de Desde DB: " + userList.size());
                    mutableLiveData.postValue(userList);
                    progressDialog.dismiss();
                }else {
                    System.out.println("desde Retrofit");

                    DataServices userDataServices = RetrofitInstance.getDataServices();
                    Call<List<User>> call = userDataServices.getUsers();

                    call.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                            if(response.isSuccessful()){
                                List<User>  resultPosts = response.body();
                                if(resultPosts != null){

                                    for (User item: resultPosts) {
                                        new InsertUserAsyncTask(userDao).execute(item);

                                    }
                                    users = response.body();
                                    mutableLiveData.postValue(users);


                                }
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }

        });

        mutableLiveData.postValue(users);


        return mutableLiveData;
    }

    private  static  class GetUserAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDAO;

        public GetUserAsyncTask(UserDao userDAO){
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(Void... users) {

           // userDAO.getUsers();



          //  System.out.println("AQUI ESTA HACIENDO LA PRUEBA: " + userDAO.getUsers().size());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println();
        }
    }

    private  static  class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDAO;

        public InsertUserAsyncTask(UserDao userDAO){
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... categories) {

            userDAO.insert(categories[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println("Guardado User");
        }
    }

}
