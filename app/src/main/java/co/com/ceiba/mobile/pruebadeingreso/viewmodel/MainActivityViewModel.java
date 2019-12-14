package co.com.ceiba.mobile.pruebadeingreso.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.source.UserRepository;

public class MainActivityViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getBaseContext();
        userRepository = new UserRepository(application);

    }
    public LiveData<List<User>> getAllUser(Context context){

        return userRepository.getLiveData(context);
    }
}
