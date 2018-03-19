package com.david.clothshop.net.service;

import com.david.clothshop.net.Config;
import com.david.clothshop.net.bean.GoodDetailResponseBean;
import com.david.clothshop.net.bean.GoodListInHome;
import com.david.clothshop.net.bean.RequestData;
import com.david.clothshop.net.bean.ResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by luozhanwei on 18-3-19.
 */

public interface GoodDetailRequestService {
    @POST(Config.GET_GOOD_DETAIL)
    Call<ResponseData<GoodDetailResponseBean>> getGoodDetail(@Body RequestData requestData);
}
