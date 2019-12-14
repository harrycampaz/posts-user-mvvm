package co.com.ceiba.mobile.pruebadeingreso.source.remote.services;

import java.util.List;
import java.util.Map;

import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface DataServices {


    @GET(Endpoints.GET_USERS)
    Call<List<User>> getUsers();

    @GET(Endpoints.GET_POST_USER)
    Call<List<Post>> getPosts(@QueryMap Map<String, Integer> params);


}
