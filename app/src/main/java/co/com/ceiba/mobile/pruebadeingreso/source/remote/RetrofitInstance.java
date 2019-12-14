package co.com.ceiba.mobile.pruebadeingreso.source.remote;

import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import co.com.ceiba.mobile.pruebadeingreso.source.remote.services.DataServices;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;

    public  static DataServices getDataServices(){

        if(retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Endpoints.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

       return  retrofit.create(DataServices.class);
    }
}
