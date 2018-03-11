package com.david.clothshop.net.service;

import com.david.clothshop.net.Config;
import com.david.clothshop.net.bean.GoodListInHome;
import com.david.clothshop.net.bean.RequestData;
import com.david.clothshop.net.bean.ResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by luxiaolin on 18/2/16.
 */

public interface HomeListRequestService {
    @POST(Config.GET_GOOD_LIST_IN_HOME)
    Call<ResponseData<GoodListInHome>> getGoodList(@Body RequestData requestData);
}
