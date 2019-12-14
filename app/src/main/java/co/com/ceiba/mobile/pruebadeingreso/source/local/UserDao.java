package co.com.ceiba.mobile.pruebadeingreso.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * from users_table")
    List<User> getUsers();

    @Insert
    void insert(User user);

}
