package com.rockstar.buspassvitran.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rockstar on 05-05-2017.
 */

public class APIClient {
    public static final String BASE_URL="https://liquid-stairs.000webhostapp.com/santosh/saurabh/saurabh/user/";

    private static Retrofit retrofit=null;

    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
