package co.com.ceiba.mobile.pruebadeingreso.source.local;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.model.Post;

@Dao
public interface PostDao {

    @Query("SELECT * from posts_table WHERE userId == :userId")
    List<Post> getPost(int userId);

    @Insert
    void insert(Post post);

}
