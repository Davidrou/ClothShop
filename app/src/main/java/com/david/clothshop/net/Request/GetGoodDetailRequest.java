package com.david.clothshop.net.Request;

import com.david.clothshop.net.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by luxiaolin on 18/3/18.
 */

public class GetGoodDetailRequest {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.SERVER_ADDRESS_BASE_LOCAL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
