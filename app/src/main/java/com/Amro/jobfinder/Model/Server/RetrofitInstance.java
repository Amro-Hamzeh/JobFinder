package com.Amro.jobfinder.Model.Server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//Retrofit instance class, as I'm using retrofit for handling APIs requests/responses.
public class RetrofitInstance {
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance(String base_url) {


            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }
}
